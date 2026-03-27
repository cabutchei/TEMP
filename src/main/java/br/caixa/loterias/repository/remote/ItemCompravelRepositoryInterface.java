package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
public interface ItemCompravelRepositoryInterface extends PanacheRepository<ItemCompravel> {

    List<ItemCompravel> findItensCompraveisByCompra(Long idCompra);
}
