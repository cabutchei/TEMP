package br.caixa.loterias.repository.remote.impl;

import java.util.List;

import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.repository.remote.ItemCompravelRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemCompravelRepository implements ItemCompravelRepositoryInterface {

    public List<ItemCompravel> findItensCompraveisByCompra(Long idCompra) {
        return find("compra.id = ?1", idCompra)
                .stream()
                .toList();
    }
}
