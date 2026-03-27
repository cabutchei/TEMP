package br.caixa.loterias.repository.remote.impl;

import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import br.caixa.loterias.repository.remote.MatrizPrognosticoRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MatrizPrognosticoRepository implements MatrizPrognosticoRepositoryInterface {

    @Override
    public List<MatrizPrognostico> findByApostaId(Long apostaId) {
        if (apostaId == null) {
            return java.util.Collections.emptyList();
        }

        return list("apostaAssociada.id", apostaId);
    }
}
