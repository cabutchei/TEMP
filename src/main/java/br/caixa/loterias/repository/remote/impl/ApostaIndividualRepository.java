package br.caixa.loterias.repository.remote.impl;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.repository.remote.ApostaIndividualRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApostaIndividualRepository implements ApostaIndividualRepositoryInterface {

    @Override
    public ApostaIndividual findApostaById(Long id) {
        return findById(id);
    }

    @Override
    public Boolean concursoPodeSerFechado(Long concursoAlvo, Long modalidade) {
        Long count = count(
            "concursoAlvo = ?1 and modalidade = ?2 and situacao in (?3, ?4, ?5, ?6)",
            concursoAlvo,
            modalidade,
            SituacaoApostaIndividualEnum.NAO_REGISTRADA,
            SituacaoApostaIndividualEnum.EM_PROCESSAMENTO,
            SituacaoApostaIndividualEnum.EM_PROCESSAMENTO_ENVIADA_SISPL,
            SituacaoApostaIndividualEnum.PAGAMENTO_EM_PROCESSAMENTO
        );

        return count > 0 ? Boolean.FALSE : Boolean.TRUE;
    }
}
