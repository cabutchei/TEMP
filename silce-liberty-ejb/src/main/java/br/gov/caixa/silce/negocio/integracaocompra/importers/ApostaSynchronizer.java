package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.AbstractApostaNumerica;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ComboApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.ApostaMapper;

public class ApostaSynchronizer extends AbstractIdNuvemLegadoSynchronizer<NuvemIntegracaoAposta, Aposta<?>> {

    private static final Logger LOG = LogManager.getLogger(ApostaSynchronizer.class, new MessageFormatMessageFactory());

    private ComboApostaDAOLocal comboApostaDAO;
    private CompraDAOLocal compraDAO;
    private ApostaDAOLocal apostaDAO;
    private ReservaCotaBolaoDAOLocal reservaCotaBolaoDAO;

    public ApostaSynchronizer(SyncContext.EntityCache cache) throws NamingException {
        // super(cache);
        this.cache = cache;
        this.comboApostaDAO = getDao(ComboApostaDAOLocal.class);
        this.compraDAO = getDao(CompraDAOLocal.class);
        this.apostaDAO = getDao(ApostaDAOLocal.class);
        this.reservaCotaBolaoDAO = getDao(ReservaCotaBolaoDAOLocal.class);
        this.dao = apostaDAO;
        this.mapper = new ApostaMapper();
    }

    @Override
    protected Aposta<?> create(NuvemIntegracaoAposta apostaNuvem) {
        Aposta<?> aposta = getCache().find(apostaNuvem.idNuvem, Aposta.class);
        resolve(apostaNuvem, aposta);
        apostaDAO.insert(aposta);
        return aposta;
    }

    @Override
    protected Aposta<?> update(NuvemIntegracaoAposta apostaNuvem) {
        Long idLegadoAposta = apostaNuvem.idLegado;
        if (idLegadoAposta == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }
        Aposta<?> aposta = getCache().find(apostaNuvem.idNuvem, Aposta.class);
        if (aposta == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idLegadoAposta);
        }
        resolve(apostaNuvem, aposta);
        Aposta<?> x = apostaDAO.update(aposta);
        if (x != aposta) {
            throw new IllegalStateException("entidade aposta retornada não é a mesma do cache");
        }

        return aposta;
    }

    @Override
    protected void resolve(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        resolveCompra(apostaNuvem, aposta);
        resolveCombo(apostaNuvem, aposta);
        if (apostaNuvem.reservaCotaBolao != null) {
            resolveReservaCotaBolao(apostaNuvem, aposta);
        }

        Compra compra = aposta.getCompra();
        aposta.setParticao(compra.getParticao());
        aposta.setMes(compra.getMes());

        if (aposta instanceof AbstractApostaNumerica) {
            AbstractApostaNumerica apostaNumerica = (AbstractApostaNumerica) aposta;
            apostaNumerica.setQuantidadeTeimosinhas(apostaNuvem.qtdTeimosinhas);
        }

        aposta.setApostaOriginalLotomania(null);
        aposta.setComboAposta(null);
    }

    private void resolveCompra(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (apostaNuvem.compra == null) {
            throw new IllegalStateException("Não há compra associada a essa aposta");
        }
        Long idCompraNuvem = apostaNuvem.compra.getIdNuvem();
        Compra compra = getCache().find(idCompraNuvem, Compra.class);
        if (compra == null) {
            throw new IllegalStateException("Nenhuma compra encontrada no cache para o id " + idCompraNuvem);
        }
        aposta.setCompra(compra);
    }

    private void resolveCombo(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        if (apostaNuvem.idNuvemCombo == null) return;
        ComboAposta combo = getCache().find(apostaNuvem.idNuvemCombo, ComboAposta.class);
        if (combo == null) {
            throw new IllegalStateException(
                "Nenhum combo encontrado no cache para o id " + apostaNuvem.idNuvemCombo);
        }
        aposta.setComboAposta(combo);
    }

    private void resolveReservaCotaBolao(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        ReservaCotaBolao reserva = getCache().find(apostaNuvem.reservaCotaBolao.idNuvem, ReservaCotaBolao.class);
        if (reserva == null) {
            throw new IllegalStateException("Nenhuma reserva de cota bolao encontrada no cache para o id " + apostaNuvem.reservaCotaBolao.idNuvem);
        }
        aposta.setReservaCotaBolao(reserva);
    }
}
