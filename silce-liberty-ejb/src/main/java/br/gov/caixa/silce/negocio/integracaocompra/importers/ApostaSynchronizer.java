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
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAO;
import br.gov.caixa.silce.negocio.aposta.dao.ComboApostaDAO;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAO;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAO;

public class ApostaSynchronizer extends IntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>, Long> {

    private static final Logger LOG =
        LogManager.getLogger(ApostaSynchronizer.class, new MessageFormatMessageFactory());

    private ComboApostaDAO comboApostaDAO;
    private CompraDAO compraDAO;
    private ApostaDAO apostaDAO;
    private ReservaCotaBolaoDAO reservaCotaBolaoDAO;

    public ApostaSynchronizer(SyncContext context) {
        super(context);
        try {
            this.comboApostaDAO = getDao(ComboApostaDAO.class);
            this.compraDAO = getDao(CompraDAO.class);
            this.apostaDAO = getDao(ApostaDAO.class);
            this.reservaCotaBolaoDAO = getDao(ReservaCotaBolaoDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    @Override
    protected Aposta<?> prepareUpdate(NuvemIntegracaoAposta apostaNuvem) {
        Aposta<?> entity = dao.findById(apostaNuvem.idLegado);
        mapper.map(apostaNuvem, entity);
        return entity;
    }

    @Override
    protected Aposta<?> create(NuvemIntegracaoAposta apostaNuvem) {

        Aposta<?> aposta =
            this.context.getCache().find(apostaNuvem.idNuvem, Aposta.class);

        resolveRelations(apostaNuvem, aposta);

        if (aposta instanceof AbstractApostaNumerica) {
            AbstractApostaNumerica apostaNumerica = (AbstractApostaNumerica) aposta;
            apostaNumerica.setQuantidadeTeimosinhas(apostaNuvem.qtdTeimosinhas);
        }

        aposta.setApostaOriginalLotomania(null);
        aposta.setComboAposta(null);

        apostaDAO.insert(aposta);

        return aposta;
    }

    @Override
    protected Aposta<?> update(NuvemIntegracaoAposta apostaNuvem) {

        Long idLegadoAposta = apostaNuvem.idLegado;

        if (idLegadoAposta == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }

        Aposta<?> aposta =
            this.context.getCache().find(apostaNuvem.idNuvem, Aposta.class);

        if (aposta == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idLegadoAposta);
        }

        if (apostaNuvem.reservaCotaBolao != null) {
            ReservaCotaBolao reserva =
                this.context.getCache().find(
                    apostaNuvem.reservaCotaBolao.idNuvem,
                    ReservaCotaBolao.class);

            aposta.setReservaCotaBolao(reserva);
        }

        Aposta<?> x = apostaDAO.update(aposta);

        if (x != aposta) {
            throw new IllegalStateException("entidade aposta retornada não é a mesma do cache");
        }

        return aposta;
    }

    @Override
    protected void resolveRelations(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        resolveCompra(apostaNuvem, aposta);
        resolveCombo(apostaNuvem, aposta);
        resolveReservaCotaBolao(apostaNuvem, aposta);

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

        Long idCompraNuvem = apostaNuvem.compra.getIdNuvem();

        Compra compra = this.context.getCache().find(idCompraNuvem, Compra.class);

        if (compra == null) {
            throw new IllegalStateException("Nenhuma compra encontrada no cache para o id " + idCompraNuvem);
        }

        aposta.setCompra(compra);
    }

    private void resolveCombo(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {

        if (apostaNuvem.idNuvemCombo == null) return;

        ComboAposta combo =
            this.context.getCache().find(apostaNuvem.idNuvemCombo, ComboAposta.class);

        if (combo == null) {
            throw new IllegalStateException(
                "Nenhum combo encontrado no cache para o id " + apostaNuvem.idNuvemCombo);
        }

        aposta.setComboAposta(combo);
    }

    private void resolveReservaCotaBolao(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {

        if (apostaNuvem.reservaCotaBolao == null) {
            aposta.setReservaCotaBolao(null);
            return;
        }

        ReservaCotaBolao reserva =
            this.context.getCache().find(
                apostaNuvem.reservaCotaBolao.idNuvem,
                ReservaCotaBolao.class);

        if (reserva == null) {
            throw new IllegalStateException(
                "Nenhuma reserva de cota bolao encontrada no cache para o id "
                    + apostaNuvem.reservaCotaBolao.idNuvem);
        }

        aposta.setReservaCotaBolao(reserva);
    }
}
