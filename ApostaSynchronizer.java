public import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

public class ApostaSynchronizer extends AbstractIntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>> {

    private static final Logger LOG =
        LogManager.getLogger(ApostaSynchronizer.class, new MessageFormatMessageFactory());

    private ComboApostaDAO comboApostaDAO;
    private CompraDAO compraDAO;
    private ApostaDAO apostaDAO;
    private ReservaCotaBolaoDAO reserveCotaBolaoDAO;

    // Stateful two-step synchronization
    private NuvemIntegracaoAposta pendingDto;
    private Aposta<?> pendingAposta;
    private Mode pendingMode;
    private boolean prepared;

    public ApostaSynchronizer() {
        try {
            this.cache = SyncContext.getInstance();
            this.comboApostaDAO = getDao(ComboApostaDAO.class);
            this.compraDAO = getDao(CompraDAO.class);
            this.apostaDAO = getDao(ApostaDAO.class);
            this.reserveCotaBolaoDAO = getDao(ReservaCotaBolaoDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    /**
     * Phase 1:
     * Maps DTO data and prepares the entity instance, but does not resolve
     * inter-entity dependencies from the cache yet.
     */
    public void preSynchronize(NuvemIntegracaoAposta apostaNuvem, Mode mode, boolean flush) {
        if (apostaNuvem == null) {
            throw new IllegalStateException("DTO da aposta é null");
        }

        ApostaMapper mapper = new ApostaMapper();
        Aposta<?> aposta = mapper.map(apostaNuvem);

        switch (mode) {
            case CREATE:
                prepareCreate(apostaNuvem, aposta);
                break;
            case UPDATE:
                prepareUpdate(apostaNuvem, aposta);
                break;
            default:
                throw new IllegalStateException("Modo não suportado: " + mode);
        }

        this.pendingDto = apostaNuvem;
        this.pendingAposta = aposta;
        this.pendingMode = mode;
        this.prepared = true;

        if (flush) {
            // Optional only if you really want preSynchronize to be able to force DB sync.
            // Usually I would leave flushing to synchronize(), but keeping this because you asked for it.
            apostaDAO.flush();
        }
    }

    /**
     * Phase 2:
     * Resolves relations from SyncContext and persists/updates the prepared entity.
     * Throws early if resolution fails.
     */
    public Aposta<?> synchronize(boolean flush) {
        ensurePrepared();

        try {
            resolveRelations(this.pendingDto, this.pendingAposta);

            Aposta<?> persisted;
            switch (this.pendingMode) {
                case CREATE:
                    apostaDAO.insert(this.pendingAposta);
                    persisted = this.pendingAposta;
                    break;
                case UPDATE:
                    persisted = apostaDAO.update(this.pendingAposta);
                    break;
                default:
                    throw new IllegalStateException("Modo não suportado: " + this.pendingMode);
            }

            if (flush) {
                apostaDAO.flush();
            }

            return persisted;
        } finally {
            clearState();
        }
    }

    @Override
    protected Aposta<?> sync(NuvemIntegracaoAposta apostaNuvem, Mode mode, boolean flush) {
        preSynchronize(apostaNuvem, mode, false);
        return synchronize(flush);
    }

    private void prepareCreate(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        applySimpleFields(apostaNuvem, aposta);

        // In create mode, usually ID must come from the incoming authoritative identity.
        if (apostaNuvem.idLegado == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }

        aposta.setId(apostaNuvem.idLegado);
    }

    private void prepareUpdate(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        Long idLegadoAposta = apostaNuvem.idLegado;

        if (idLegadoAposta == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }

        Aposta<?> current = apostaDAO.findById(idLegadoAposta);

        if (current == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idLegadoAposta);
        }

        applySimpleFields(apostaNuvem, aposta);

        // Keep the same local identity so update() targets the correct row/entity.
        aposta.setId(idLegadoAposta);

        // If there are fields that must be preserved from current because they are not mapped
        // from the DTO, keep that logic here.
        aposta.setReservaCotaBolao(current.getReservaCotaBolao());
    }

    /**
     * Only simple/scalar fields here.
     * No cache lookups, no DAO lookups for dependent entities.
     */
    private void applySimpleFields(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (aposta instanceof AbstractApostaNumerica) {
            AbstractApostaNumerica apostaNumerica = (AbstractApostaNumerica) aposta;
            apostaNumerica.setQuantidadeTeimosinhas(apostaNuvem.qtdTeimosinhas);
        }

        // Leave relation fields unresolved for phase 2.
        aposta.setApostaOriginalLotomania(null); // TODO APOSTA ESPELHO
        aposta.setComboAposta(null);
        aposta.setCompra(null);
        aposta.setReservaCotaBolao(null);
        aposta.setParticao(null);
        aposta.setMes(null);
    }

    /**
     * All relation resolution happens here.
     * Fail immediately if required dependencies are missing.
     */
    private void resolveRelations(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        resolveCompra(apostaNuvem, aposta);
        resolveCombo(apostaNuvem, aposta);
        resolveReservaCotaBolao(apostaNuvem, aposta);

        // Derived values that depend on resolved relations
        Compra compra = aposta.getCompra();
        aposta.setParticao(compra.getParticao());
        aposta.setMes(compra.getMes());
    }

    private void resolveCompra(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (apostaNuvem.compra == null) {
            throw new IllegalStateException("Não há compra associada a essa aposta");
        }

        Long idCompra = apostaNuvem.compra.idLegado;
        if (idCompra == null) {
            throw new IllegalStateException("Nenhum id legado associado à compra");
        }

        Compra compra = cache.find(idCompra, Compra.class);
        if (compra == null) {
            throw new IllegalStateException("Nenhuma compra encontrada no cache para o id " + idCompra);
        }

        aposta.setCompra(compra);
    }

    private void resolveCombo(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (apostaNuvem.idNuvemCombo == null) {
            aposta.setComboAposta(null);
            return;
        }

        ComboAposta combo = cache.find(apostaNuvem.idNuvemCombo, ComboAposta.class);

        if (combo == null) {
            throw new IllegalStateException(
                "Nenhum combo encontrado no cache para o id " + apostaNuvem.idNuvemCombo
            );
        }

        aposta.setComboAposta(combo);
    }

    private void resolveReservaCotaBolao(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (apostaNuvem.reservaCotaBolao == null) {
            aposta.setReservaCotaBolao(null);
            return;
        }

        Long idLegadoReserva = apostaNuvem.reservaCotaBolao.idLegado;
        if (idLegadoReserva == null) {
            throw new IllegalStateException("id legado da reserva é null");
        }

        ReservaCotaBolao reservaCotaBolao = cache.find(idLegadoReserva, ReservaCotaBolao.class);
        if (reservaCotaBolao == null) {
            throw new IllegalStateException(
                "Nenhuma reserva de cota bolão encontrada no cache para o id " + idLegadoReserva
            );
        }

        aposta.setReservaCotaBolao(reservaCotaBolao);
    }

    private void ensurePrepared() {
        if (!prepared || pendingDto == null || pendingAposta == null || pendingMode == null) {
            throw new IllegalStateException(
                "preSynchronize() deve ser chamado antes de synchronize()"
            );
        }
    }

    private void clearState() {
        this.pendingDto = null;
        this.pendingAposta = null;
        this.pendingMode = null;
        this.prepared = false;
    }
} {
    
}
