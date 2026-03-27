package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.fechamento.Fechamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.Optional;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
public interface FechamentoRepositoryInterface extends PanacheRepository<Fechamento> {

    Optional<Fechamento> findByConcursoAndIdModalidade(Long concurso, Long modalidade);
}
