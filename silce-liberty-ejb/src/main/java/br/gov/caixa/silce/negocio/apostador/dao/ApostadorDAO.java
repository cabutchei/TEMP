package br.gov.caixa.silce.negocio.apostador.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.SituacaoApostador;
import br.gov.caixa.silce.dominio.entidade.SituacaoApostador.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Email;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.LocaleUtil;
import br.gov.caixa.util.StringUtil;

@Stateless
public class ApostadorDAO extends AbstractSilceDAO<Apostador, Long> implements ApostadorDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	public Apostador findByCPF(CPF cpf) {
		return getSingleResultByNamedQuery(Apostador.NQ_SELECT_BY_CPF, cpf);
	}

	@Override
	public Apostador findByCPFNome(CPF cpf, String nome) {
		Apostador apostador = getSingleResultByNamedQuery(Apostador.NQ_SELECT_BY_CPF_NOME, cpf.getCpfSemMascara(), removeAcentosAndUpper(nome));
		if (apostador != null) {
			apostador.getSituacao();
		}
		return apostador;
	}

	@Override
	public Apostador findByEmail(Email email) {
		return getSingleResultByNamedQuery(Apostador.NQ_SELECT_BY_EMAIL, email);
	}

	@Override
	public List<Apostador> findAtivosByDataMaximaUltimoAcesso(Data data) {
		return getResultListByNamedQuery(Apostador.NQ_SELECT_BY_DATAULTIMOACESSOMAXIMA_SITUACAO, data, Situacao.ATIVO.getValue());
	}

	@Override
	public List<Apostador> findAtivosByDataUltimoAcesso(Data data) {
		return getResultListByNamedQuery(Apostador.NQ_SELECT_BY_DATAULTIMOACESSO_SITUACAO, data, Situacao.ATIVO.getValue());
	}

	@Override
	public Apostador findByCPFWithDifferentId(Long id, CPF cpf) {
		return getSingleResultByNamedQuery(Apostador.NQ_SELECT_BY_DIFFERENTID_CPF, id, cpf);
	}

	@Override
	public ResultadoPesquisaPaginada<Apostador> findByCpfEmailSituacao(CPF cpf, Email email, String nome, Situacao situacao, Long loterica, int first, int pageSize) {
		String jpql = selectQuery + " order by entidade.nome, entidade.id desc";
		StringBuilder sb = new StringBuilder(jpql);
		Map<String, Object> parametros = newParametersMap();

		if (cpf != null) {
			parametros.put("and entidade.cpf =", cpf);
		}
		if (email != null) {
			parametros.put("and entidade.email =", email);
		}
		if (situacao != null) {
			parametros.put("and entidade.situacao.id =", situacao.getValue());
		}
		if (loterica != null) {
			parametros.put("and entidade.loterica.id =", loterica);
		}
		if (!StringUtil.isEmpty(nome)) {
			parametros.put("and UPPER(entidade.nome) =", removeAcentosAndUpper(nome));
		}

		return getResultListByQuery(first, pageSize, sb.toString(), newForceFetchMap(Apostador.class, "entidade"), parametros, getHintsMapWithUR());
	}

	@Override
	public Long findIdentificadorByCPF(CPF cpf) {
		return getSingleResultByNamedQuery(Apostador.NQ_SELECT_ID_BY_CPF, cpf);
	}

	@Override
	public List<Object[]> findQuantidadeUsuariosCompraram(Data dataInicio, Data dataFim) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", LocaleUtil.PT_BR);
		String stringDataInicio = format.format(dataInicio.getTime());
		String stringDataFim = format.format(dataFim.getTime());
		return getResultListByNamedQuery(Apostador.NQ_SELECT_COUNT_COMPRARAM, stringDataInicio, stringDataFim);
	}

	@Override
	public List<Object[]> findQuantidadeUsuariosSemCompra(Data dataInicioEnviada, Data dataFimEnviada) {
		String stringDataInicio = DataUtil.dataToString(dataInicioEnviada, DATA_DB2_FORMAT);
		String stringDataFim = DataUtil.dataToString(dataFimEnviada, DATA_DB2_FORMAT);
		return getResultListByNamedQuery(Apostador.NQ_SELECT_COUNT_NAO_COMPRARAM, stringDataInicio, stringDataFim);
	}

	@Override
	public List<Object[]> findQuantidadeUsuariosNovos(Data dataInicio, Data dataFim) {
		String stringTimestampInicio = DataUtil.dataToString(dataInicio, TIMESTAMP_DB2_FORMAT);
		String stringTimestampFim = DataUtil.dataToString(dataFim, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Apostador.NQ_SELECT_COUNT_CADASTRARAM, stringTimestampInicio, stringTimestampFim);
	}

	@Override
	public List<Object[]> findQuantidadeUsuariosTotal() {
		return getResultListByNamedQuery(Apostador.NQ_SELECT_COUNT_CADASTRARAM_TOTAL);
	}

	@Override
	public List<Object[]> findQuantidadeUsuariosApenasComprasNegadas(Data dataInicioEnviada, Data dataFimEnviada) {
		String stringDataInicio = DataUtil.dataToString(dataInicioEnviada, TIMESTAMP_DB2_FORMAT);
		String stringDataFim = DataUtil.dataToString(dataFimEnviada, TIMESTAMP_DB2_FORMAT);
		List<Object[]> result = getResultListByNamedQuery(Apostador.NQ_SELECT_COUNT_APENAS_COMPRAS_NEGADAS, stringDataInicio, stringDataFim);
		if (result == null) {
			result = new ArrayList<Object[]>();
		}
		return result;
	}

	@Override
	public ResultadoPesquisaPaginada<Apostador> findApostadorImpedidoSicow(CPF cpf, Data dataInicio, Data dataFim, int first, int pageSize) {
		String jpql = selectQuery + " order by entidade.nome, entidade.id desc";
		StringBuilder sb = new StringBuilder(jpql);
		Map<String, Object> parametros = newParametersMap();

		parametros.put("and entidade.situacao.id in ", convertToIdList(SituacaoApostador.Situacao.getSituacoesBloqueioSicow()));

		if (cpf != null) {
			parametros.put("and entidade.cpf = ", cpf);
		}
		if (dataInicio != null && dataFim != null) {
			Hora horaInicio = new Hora("00:00:00");
			Hora horaFim = new Hora("23:59:59");
			dataInicio.setHora(horaInicio);
			dataFim.setHora(horaFim);
			parametros.put("and entidade.dataUltimaConsultaSicow >= ", dataInicio);
			parametros.put("and entidade.dataUltimaConsultaSicow <= ", dataFim);
		}

		return getResultListByQuery(first, pageSize, sb.toString(), newForceFetchMap(Apostador.class, "entidade"), parametros, getHintsMapWithUR());
	}
}
