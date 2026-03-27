package br.caixa.loterias.repository.remote.impl;

import br.caixa.loterias.model.registroenviointegracao.RegistroEnvioIntegracao;
import br.caixa.loterias.repository.remote.RegistroEnvioIntegracaoRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegistroEnvioIntegracaoRepository implements RegistroEnvioIntegracaoRepositoryInterface {

    @Override
    public RegistroEnvioIntegracao findMensagemById(Long id) {
        return this.findById(id);
    }

    @Override
    public void update(RegistroEnvioIntegracao registroEnvio) {
        persist(registroEnvio);
    }
}
