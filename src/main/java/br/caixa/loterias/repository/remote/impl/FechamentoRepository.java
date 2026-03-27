package br.caixa.loterias.repository.remote.impl;

import br.caixa.loterias.model.fechamento.Fechamento;
import br.caixa.loterias.repository.remote.FechamentoRepositoryInterface;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class FechamentoRepository implements FechamentoRepositoryInterface,
        PanacheRepositoryBase<Fechamento, Long> {

    @Override
    public Optional<Fechamento> findByConcursoAndIdModalidade(Long concurso, Long modalidade) {
        return find("idConcurso = ?1 and idModalidade = ?2", concurso, modalidade)
                .stream()
                .findFirst();
    }
}
