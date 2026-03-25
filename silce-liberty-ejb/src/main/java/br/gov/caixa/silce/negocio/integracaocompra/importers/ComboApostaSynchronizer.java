package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.negocio.aposta.dao.ComboApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.TipoComboDAOLocal;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.ComboApostaMapper;


public class ComboApostaSynchronizer extends AbstractIdNuvemLegadoSynchronizer<NuvemIntegracaoCombo, ComboAposta> {

    private ComboApostaDAOLocal comboApostaDAO;
    private TipoComboDAOLocal tipoComboDAO;

    public ComboApostaSynchronizer(SyncContext.EntityCache cache) throws NamingException {
        this.comboApostaDAO = getDao(ComboApostaDAOLocal.class);
        this.tipoComboDAO = getDao(TipoComboDAOLocal.class);
        this.dao = comboApostaDAO;
        this.cache = cache;
        this.mapper = new ComboApostaMapper();
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
        ComboAposta combo = getCache().find(comboNuvem.getIdNuvem(), ComboAposta.class);
        if (combo == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idLegadoCombo);
        }
        if (comboNuvem.idLegado == null) throw new IllegalStateException("id legado do combo é null");
        resolve(comboNuvem, combo);
        ComboAposta x = comboApostaDAO.update(combo);
        if (x != combo) throw new IllegalStateException("entidade combo retornada não é a mesma do cache");
        return combo;
    }

    @Override
    protected ComboAposta create(NuvemIntegracaoCombo comboNuvem) {

        ComboAposta combo = getCache().find(comboNuvem.getIdNuvem(), ComboAposta.class);

        resolve(comboNuvem, combo);

        combo.setMes((long) IntegracaoUtil.getMesAtual());
        combo.setParticao((long) IntegracaoUtil.getParticao(comboNuvem.getIdNuvem()));
        // obs: o campo de data é transiente e, até onde investiguei, nem sequer é preenchido no fluxo atual do carrinho nuvem
        // combo.setDataInclusao(new Data(new Date())).toString(); // TODO: melhorar essa aberração. criar o campo na nuvem?
        combo.setTipoCombo(comboNuvem.tipoCombo);
        comboApostaDAO.insert(combo);
        return combo;
    }

    @Override
    protected void resolve(NuvemIntegracaoCombo comboNuvem, ComboAposta combo) {
        combo.setTipoCombo(recuperarTipoCombo(1L));
    }

    private TipoCombo recuperarTipoCombo(Long tipoCombo) {
        return tipoComboDAO.findById(tipoCombo);
    }
}
