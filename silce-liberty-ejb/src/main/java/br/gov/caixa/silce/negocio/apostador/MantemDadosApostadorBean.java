package br.gov.caixa.silce.negocio.apostador;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.Transacao;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.ApostadorVO;
import br.gov.caixa.silce.dominio.entidade.CompraSumarizadaPorSituacao;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.Municipio;
import br.gov.caixa.silce.dominio.entidade.Parametro.ParametroSistema;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.entidade.SituacaoApostador.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SuspensaoTemporariaApostador;
import br.gov.caixa.silce.dominio.entidade.TelefoneVO;
import br.gov.caixa.silce.dominio.entidade.TermoDeUso;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Sexo;
// import br.gov.caixa.silce.negocio.aposta.dao.PremioDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAOLocal;
// import br.gov.caixa.silce.negocio.apostador.dao.SituacaoApostadorDAOLocal;
// import br.gov.caixa.silce.negocio.apostador.dao.SuspensaoApostadorDAOLocal;
import br.gov.caixa.silce.negocio.infra.SilceBean;
// import br.gov.caixa.silce.negocio.interfaces.TrilhaAuditoria.NomeCampo;
// import br.gov.caixa.silce.negocio.interfaces.local.ConsultaComprasLocal;
// import br.gov.caixa.silce.negocio.interfaces.local.EnderecoSIICOLocal;
import br.gov.caixa.silce.negocio.interfaces.local.MantemDadosApostadorLocal;
// import br.gov.caixa.silce.negocio.interfaces.local.MantemParametrosSistemaLocal;
// import br.gov.caixa.silce.negocio.interfaces.local.MantemTermosDeUsoLocal;
// import br.gov.caixa.silce.negocio.interfaces.local.PesquisaCadastralSIPESLocal;
// import br.gov.caixa.silce.negocio.interfaces.local.TrilhaAuditoriaLocal;
// import br.gov.caixa.silce.negocio.interfaces.remote.MantemDadosApostadorRemote;
// import br.gov.caixa.sispl.trilhaauditoria.dominio.Campo;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;
import br.gov.caixa.util.Telefone;
import br.gov.caixa.util.Validate;

/**
 * Atende ao UC - Mantem Dados do Apostador.
 */
@Stateless
public class MantemDadosApostadorBean implements MantemDadosApostadorLocal, SilceBean {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(MantemDadosApostadorBean.class, new MessageFormatMessageFactory());
	private static final String DATA_INICIO = "Data Início";
	private static final String DATA = "Data";
	private static final String CPF = "CPF";

	@EJB
	private ApostadorDAOLocal apostadorDAO;

	@EJB
	private LotericaDAOLocal lotericaDAO;

	// private transient MantemDadosApostadorValidacao validacao = new MantemDadosApostadorValidacao();
	

	@Override
	public void altereMercadoPagoCustomerId(Long idApostador, String mercadopagoCustomerId) {
		Apostador apostador = apostadorDAO.findById(idApostador);
		apostador.setMercadoPagoCustomerId(mercadopagoCustomerId);
		apostadorDAO.update(apostador);
	}

	@Override
	public void altereDataFimBloqueioVinculacaoCarteira(Long idApostador, Data novaData) throws NegocioException {
		Apostador apostador = apostadorDAO.findById(idApostador);
		apostador.setDataFimBloqueioVinculacao(novaData);
		apostadorDAO.update(apostador);
	}

	@Override
	public void altereTelefone(Long idApostador, Telefone telefone) throws NegocioException {
		Apostador apostador = apostadorDAO.findById(idApostador);
		apostador.setTelefone(telefone);
		apostadorDAO.update(apostador);
	}

	@Override
	public Apostador recupereApostador(Long id) {
		return apostadorDAO.findById(id);
	}

	@Override
	public Apostador recupereApostador(Email email) {
		return apostadorDAO.findByEmail(email);
	}

	@Override
	public Apostador recupereApostador(CPF cpf) {
		return apostadorDAO.findByCPF(cpf);
	}
	
	public Apostador recupereApostador(CPF cpf, String nome) {
		return apostadorDAO.findByCPFNome(cpf, nome);
	}

	@Override
	public ResultadoPesquisaPaginada<Apostador> buscaApostadores(CPF cpf, Email email, String nome, Situacao situacao, Long loterica, int first, int pageSize) {
		LOG.debug("Realizando busca de apostadores. CPF: {0}, Email: {1}, Situação: {2}, Loterica: {3}, Nome: {4}", cpf, email, situacao, loterica, nome);
		return apostadorDAO.findByCpfEmailSituacao(cpf, email, nome, situacao, loterica, first, pageSize);
	}

	/**
	 * Garantia programática de que não serão alterados campos que, na DI, não podem ser alterados. O UC não manda fazer
	 * esse fluxo, pois considera que os campos nunca serão alterados, já que a DI não permite. Programaticamente,
	 * sabemos que os campos podem estar sendo alterados, e esse método garante que não serão.
	 */
	private void reverteDadosNaoAlteraveis(Apostador apostadorOriginal, Apostador apostadorNovo) {
		apostadorNovo.setNome(apostadorOriginal.getNome());
		apostadorNovo.setCpf(apostadorOriginal.getCpf());
		apostadorNovo.setDataNascimento(apostadorOriginal.getDataNascimento());
	}

	// @Override
	// public void aceitarTermoVigente(Long idApostador, Boolean receberNoticias) {
	// 	TermoDeUso termoDeUsoVigente = mantemTermosDeUsoLocal.recupereTermoDeUsoVigente();
	// 	Apostador apostador = recupereApostador(idApostador);
	// 	apostador.setUltimoTermoDeUsoAceito(termoDeUsoVigente);
	// 	apostador.setDataUltimoAceiteTermoDeUso(DataUtil.getTimestampAtual());
	// 	apostador.setAceitaReceberNoticias(receberNoticias);
	// 	apostador.setAceitaTermosUso(Boolean.TRUE);
	// 	apostadorDAO.update(apostador);
	// }

	@Override
	public Long recupereIdentificadorApostadorCPF(CPF cpf) {
		return apostadorDAO.findIdentificadorByCPF(cpf);
	}

	private boolean isDataMaiorDataAtual(Data data) {
		Data dataAtual = DataUtil.getDataAtual();
		return data.after(dataAtual);
	}

	private void valideDataInicioFim(Data dataInicio, Data dataFim) throws NegocioException {
		if (dataInicio != null && isDataMaiorDataAtual(dataInicio)) {
			throw new NegocioException(CodigoErro.DATA_MAIOR_DATA_ATUAL, DATA_INICIO);
		}
		if (dataInicio != null && dataFim != null && dataFim.before(dataInicio)) {
			throw new NegocioException(CodigoErro.HISTORICO_COMPRAS_DATA_FIM_MENOR_QUE_DATA_INICIO);
		}
	}


	public Apostador alteraDataConsultaSicow(Long idApostador, Data dataConsultaSicow) {
		Apostador apostador = apostadorDAO.findById(idApostador);
		apostador.setDataFimBloqueioVinculacao(DataUtil.getTimestampAtual());
		apostador.setDataUltimaConsultaSicow(dataConsultaSicow);
		
		return apostadorDAO.update(apostador);
	}

    @Override
    public Apostador altereUsuario(Long idApostador, Long codigoUf, Long numeroMunicipio, Long dvMunicipio, CEP cep,
            Long lotericaId, Sexo sexo, Decimal limiteDiario, TelefoneVO telefoneUsuario) throws NegocioException {
        return null;
        
    }

    @Override
    public void altereApostadorGestao(String matricula, ApostadorVO apostadorVO, Situacao situacao,
            Data dataSolicitacao, String solicitante, String justificativa) throws NegocioException {
        
        
    }

    @Override
    public void aceitarTermoVigente(Long idApostador, Boolean receberNoticias) {
        
        
    }

    @Override
    public Boolean recalculeLimiteAutorizado(Long idApostador) throws NegocioException {
        return null;
        
    }

    @Override
    public ResultadoPesquisaPaginada<Premio> buscaPremios(br.gov.caixa.util.CPF cpf, Data dataInicio, Data dataFim,
            int first, int pageSize) throws NegocioException {
        return null;
        
    }

    @Override
    public SuspensaoTemporariaApostador recuperaSuspensaoAtiva(Long idApostador) {
        return null;
        
    }

    @Override
    public SuspensaoTemporariaApostador recuperaUltimaSuspensao(Long idApostador) {
        return null;
        
    }

    @Override
    public Apostador bloqueiaApostador(Long idApostador, Situacao situacao, Data dataConsultaSicow) {
        return null;
        
    }

    @Override
    public Apostador createApostadorFromVO(ApostadorVO apostadorVO) throws NegocioException {
        return null;
        
    }	


}
