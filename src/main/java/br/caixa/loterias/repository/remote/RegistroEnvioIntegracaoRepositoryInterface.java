package br.caixa.loterias.repository.remote;

import br.caixa.loterias.model.registroenviointegracao.RegistroEnvioIntegracao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface RegistroEnvioIntegracaoRepositoryInterface extends PanacheRepository<RegistroEnvioIntegracao> {

    RegistroEnvioIntegracao findMensagemById(Long id);

    void update(RegistroEnvioIntegracao registroEnvio);
}
