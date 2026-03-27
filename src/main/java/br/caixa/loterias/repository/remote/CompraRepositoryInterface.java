package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.compra.Compra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CompraRepositoryInterface extends PanacheRepository<Compra> {

    Compra findCompraById(Long id);
}
