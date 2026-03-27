package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface ApostaIndividualRepositoryInterface extends PanacheRepository<ApostaIndividual> {

    ApostaIndividual findApostaById(Long id);

    Boolean concursoPodeSerFechado(Long concursoAlvo, Long modalidade);
}
