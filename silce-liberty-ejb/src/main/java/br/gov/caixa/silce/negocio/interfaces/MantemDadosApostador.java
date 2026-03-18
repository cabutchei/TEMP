package br.gov.caixa.silce.negocio.interfaces;

import java.io.Serializable;

import br.gov.caixa.dominio.Cacheable;
import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.ApostadorVO;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.entidade.SituacaoApostador.Situacao;
import br.gov.caixa.silce.dominio.entidade.SuspensaoTemporariaApostador;
import br.gov.caixa.silce.dominio.entidade.TelefoneVO;
import br.gov.caixa.silce.dominio.jogos.Sexo;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;
import br.gov.caixa.util.Telefone;

/**
 * Interface que define o contrato com o EJB MantenDadosApostadorBean.
 */
public interface MantemDadosApostador extends Serializable {

	public Apostador altereUsuario(Long idApostador, Long codigoUf, Long numeroMunicipio, Long dvMunicipio, CEP cep, Long lotericaId, Sexo sexo, Decimal limiteDiario,
		TelefoneVO telefoneUsuario)
		throws NegocioException;

	public void altereTelefone(Long idApostador, Telefone telefone) throws NegocioException;

	public Apostador recupereApostador(Long id);

	public Apostador recupereApostador(Email email);

	public Apostador recupereApostador(CPF cpf);

	public Apostador recupereApostador(CPF cpf, String nome);

	@Cacheable(cacheNullValues = false, timeToIdleSeconds = 120, timeToLiveSeconds = 600, maxEntriesLocalHeap = 50000)
	public Long recupereIdentificadorApostadorCPF(CPF cpf);

	public ResultadoPesquisaPaginada<Apostador> buscaApostadores(CPF cpf, Email email, String nome, Situacao situacao, Long loterica, int first, int pageSize);

	public void altereApostadorGestao(String matricula, ApostadorVO apostadorVO, Situacao situacao, Data dataSolicitacao, String solicitante,
			String justificativa) throws NegocioException;

	public void aceitarTermoVigente(Long idApostador, Boolean receberNoticias);

	public Boolean recalculeLimiteAutorizado(Long idApostador) throws NegocioException;

	public ResultadoPesquisaPaginada<Premio> buscaPremios(CPF cpf, Data dataInicio, Data dataFim, int first, int pageSize) throws NegocioException;

	public SuspensaoTemporariaApostador recuperaSuspensaoAtiva(Long idApostador);

	public SuspensaoTemporariaApostador recuperaUltimaSuspensao(Long idApostador);
	
	public Apostador bloqueiaApostador(Long idApostador, Situacao situacao, Data dataConsultaSicow);
	
	public Apostador alteraDataConsultaSicow(Long idApostador, Data dataConsultaSicow);

}
