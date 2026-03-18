package br.gov.caixa.silce.negocio.integracaocompra.mappers;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;

public class ComboApostaMapper {

    public ComboAposta map(NuvemIntegracaoCombo comboApostaNuvem) {
        ComboAposta combo = new ComboAposta();
        combo.setMes((long) IntegracaoUtil.getMesAtual());
        combo.setParticao( (long) IntegracaoUtil.getParticao(comboApostaNuvem.idNuvem)); // TODO conferir se essa geração de partição é aceitável
        // obs: o campo de data é transiente e, até onde investiguei, nem sequer é preenchido no fluxo atual do carrinho nuvem
        // combo.setDataInclusao((new Data(new Date())).toString()); // TODO melhorar essa aberração, criar o campo na nuvem
        return combo;
    }
}
