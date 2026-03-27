package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface MatrizPrognosticoRepositoryInterface extends PanacheRepository<MatrizPrognostico> {

    // Busca todas as matrizes associadas a uma aposta (por id da aposta)
    List<MatrizPrognostico> findByApostaId(Long apostaId);
}
