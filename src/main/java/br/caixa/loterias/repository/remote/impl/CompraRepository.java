package br.caixa.loterias.repository.remote.impl;

import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.repository.remote.CompraRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompraRepository implements CompraRepositoryInterface {

    @Override
    public Compra findCompraById(Long id) {
        return findById(id);
    }
}
