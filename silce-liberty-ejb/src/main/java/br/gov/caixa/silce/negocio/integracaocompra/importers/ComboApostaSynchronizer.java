package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.negocio.aposta.dao.ComboApostaDAO;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;


public class ComboApostaSynchronizer extends IntegracaoSynchronizer<NuvemIntegracaoCombo, ComboAposta, Long> {

    private ComboApostaDAO comboApostaDAO;

    public ComboApostaSynchronizer(SyncContext context) {
        super(context);
        try {
            this.comboApostaDAO = getDao(ComboApostaDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    @Override
    protected ComboAposta prepareUpdate(NuvemIntegracaoCombo comboNuvem) {
        ComboAposta entity = dao.findById(comboNuvem.getIdLegado());
        mapper.map(comboNuvem, entity);
        return entity;
    }

    @Override
    protected ComboAposta update(NuvemIntegracaoCombo comboNuvem) {
        Long idLegadoCombo = comboNuvem.getIdLegado();
        if (idLegadoCombo == null) throw new IllegalStateException("id legado do combo é null");
        ComboAposta combo = this.context.getCache().find(comboNuvem.getIdNuvem(), ComboAposta.class);
        if (combo == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idLegadoCombo);
        }
        if (comboNuvem.idLegado == null) throw new IllegalStateException("id legado do combo é null");
        ComboAposta x = comboApostaDAO.update(combo);
        if (x != combo) throw new IllegalStateException("entidade combo retornada não é a mesma do cache");
        return combo;
    }

    @Override
    protected ComboAposta create(NuvemIntegracaoCombo comboNuvem) {

        ComboAposta combo = this.context.getCache().find(comboNuvem.getIdNuvem(), ComboAposta.class);

        resolveRelations(comboNuvem, combo);

        combo.setMes((long) IntegracaoUtil.getMesAtual());
        combo.setParticao((long) IntegracaoUtil.getParticao(comboNuvem.getIdNuvem()));
        // obs: o campo de data é transiente e, at[e onde investiguei, nem sequer [e preenchido no fluxo atual do carrinho nuvem
        // combo.setDataInclusao(new Data(new Date())).toString(); // TODO: melhorar essa aberração. criar o campo na nuvem?
        comboApostaDAO.insert(combo);
        this.context.getCache().put(comboNuvem.getIdNuvem(), combo);
        return combo;
    }

    @Override
    protected void resolveRelations(NuvemIntegracaoCombo comboNuvem, ComboAposta combo) {}
}
