package br.gov.caixa.silce.negocio.apostador.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.entidade.BairroVO;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.MunicipioPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.StringUtil;

/**
 * DAO para manipulação de dados referentes é entidade lotérica.
 */
@Stateless
public class LotericaDAO extends AbstractSilceDAO<Loterica, Long> implements LotericaDAOLocal {

	private static final String FETCH_FIRST_100_ROWS_ONLY = " FETCH FIRST 100 ROWS ONLY";
	private static final long serialVersionUID = 1L;
	private static final String SELECT = "SELECT NU_CD, NU_CEP, NO_FANTASIA, NO_RAZAO_SOCIAL, NO_LOGRADOURO, NU_UF_IBGE_L98, NU_MNCPO_IBGE_L98, NU_DV_IBGE_L98, NO_BAIRRO, NO_MUNICIPIO, NO_UF, NU_SITUACAO_C04, NU_POLO_CD, NU_DV_CD, RAND() as rand "
		+ "FROM LCE.LCEVW004_LOTERICA ";
	private static final String ORDER_BY_RAND = " ORDER BY rand";

	@Override
	public List<Loterica> findByDistinctNomeBairroOrderRandom(BairroVO bairroVO, Long[] situacoes, Long[] categorias) {
		StringBuilder jpql = new StringBuilder(SELECT);
		jpql.append("WHERE RTRIM(NO_BAIRRO) LIKE ?1 and NU_UF_IBGE_L98 = ?2 and NU_MNCPO_IBGE_L98 = ?3 and NU_DV_IBGE_L98 = ?4 and NU_SITUACAO_C04 in (");

		Object[] parametros = new Object[situacoes.length + categorias.length + 4];
		int cont = 0;
		parametros[cont] = bairroVO.getNomeBairro().trim();
		cont++;
		parametros[cont] = bairroVO.getCodigoUF();
		cont++;
		parametros[cont] = bairroVO.getNumeroMunicipio();
		cont++;
		parametros[cont] = bairroVO.getDvMunicipio();

		for (Long situacao : situacoes) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao;
		}
		jpql.delete(jpql.length() - 1, jpql.length());
		jpql.append(")");

		jpql.append(" and NU_CATEGORIA_005 in(");

		for (Long categoria : categorias) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = categoria;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")").append(ORDER_BY_RAND);

		return getResultListByNativeQuery(jpql.toString(), Loterica.class, parametros);
	}

	@Override
	public List<Loterica> findByNomeFantasiaOrderRandom(String nomeFantasia, Long[] situacoes, Long[] categorias) {

		StringBuilder jpql = new StringBuilder(SELECT);
		jpql.append("WHERE TRANSLATE(NO_FANTASIA, 'aaaaeeiooouc','ãâáàéêíõôóúç') like ?1 escape '¢' and NU_SITUACAO_C04 in (");

		Object[] parametros = new Object[situacoes.length + categorias.length + 4];
		int cont = 0;
		parametros[cont] = addWildCards(removeAcentosAndUpper(nomeFantasia), '¢');

		for (Long situacao : situacoes) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")");

		jpql.append(" and NU_CATEGORIA_005 in(");

		for (Long categoria : categorias) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = categoria;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")").append(ORDER_BY_RAND);

		jpql.append(FETCH_FIRST_100_ROWS_ONLY);

		return getResultListByNativeQuery(jpql.toString(), Loterica.class, parametros);
	}

	public List<Loterica> findByNomeCodigo(String nome, Integer codigo) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM LCE.LCEVW004_LOTERICA lt WHERE ");
		if (!StringUtil.isEmpty(nome)) {
			query.append("(lt.NO_FANTASIA LIKE '%" + removeAcentosAndUpper(nome) + "%' OR lt.NO_RAZAO_SOCIAL LIKE '%" + removeAcentosAndUpper(nome) + "%') ");
		}
		if (codigo != null && !StringUtil.isEmpty(nome)) {
			query.append("OR ");
		}
		if (codigo != null) {
			query.append("(lt.NU_CD = " + codigo + ") ");
		}

		query.append("AND (lt.NU_SITUACAO_C04 in (1, 14, 15) ) ");

		return getResultListByNativeQuery(query.toString(), Loterica.class);
	}

	@Override
	public List<Loterica> findByMunicipioOrderRandom(MunicipioPK idMunicipio, Long[] situacoes, Long[] categorias) {
		// WHERE NU_UF_IBGE_L98 = ?1 and NU_MNCPO_IBGE_L98 = ?2 and NU_DV_IBGE_L98 = ?3 and NU_SITUACAO_C04 in (?4, ?5)

		StringBuilder jpql = new StringBuilder(SELECT);
		jpql.append("WHERE NU_UF_IBGE_L98 = ?1 and NU_MNCPO_IBGE_L98 = ?2 and NU_DV_IBGE_L98 = ?3 and NU_SITUACAO_C04 in (");

		Object[] parametros = new Object[situacoes.length + categorias.length + 4];
		int cont = 0;
		parametros[cont] = idMunicipio.getCodigoUF();
		cont++;
		parametros[cont] = idMunicipio.getNumero();
		cont++;
		parametros[cont] = idMunicipio.getDigitoVerificador();

		for (Long situacao : situacoes) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")");

		jpql.append(" and NU_CATEGORIA_005 in(");

		for (Long categoria : categorias) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = categoria;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")").append(ORDER_BY_RAND);

		jpql.append(FETCH_FIRST_100_ROWS_ONLY);

		return getResultListByNativeQuery(jpql.toString(), Loterica.class, parametros);
	}

	@Override
	public List<Loterica> findByUfOrderRandom(Long idUf, Long[] situacoes, Long[] categorias) {
		StringBuilder jpql = new StringBuilder(SELECT);
		jpql.append("WHERE NU_UF_IBGE_L98 = ?1 and NU_SITUACAO_C04 in (");

		Object[] parametros = new Object[situacoes.length + categorias.length + 4];
		int cont = 0;
		parametros[cont] = idUf;

		for (Long situacao : situacoes) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")");

		jpql.append(" and NU_CATEGORIA_005 in(");

		for (Long categoria : categorias) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = categoria;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")").append(ORDER_BY_RAND);

		return getResultListByNativeQuery(jpql.toString(), Loterica.class, parametros);
	}

	@Override
	public List<Loterica> findByCEPOrderRandom(CEP cep, Long[] situacoes, Long[] categorias) {

		StringBuilder jpql = new StringBuilder(SELECT);
		jpql.append("WHERE NU_CEP = ?1 and NU_SITUACAO_C04 in (");

		Object[] parametros = new Object[situacoes.length + categorias.length + 4];

		int cont = 0;
		parametros[cont] = cep.getCepSemMascara();

		for (Long situacao : situacoes) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")");

		jpql.append(" and NU_CATEGORIA_005 in(");

		for (Long categoria : categorias) {
			cont++;
			jpql.append("?" + (cont + 1) + ",");
			parametros[cont] = categoria;
		}
		jpql.delete(jpql.length() - 1, jpql.length());

		jpql.append(")").append(ORDER_BY_RAND);

		jpql.append(FETCH_FIRST_100_ROWS_ONLY);

		return getResultListByNativeQuery(jpql.toString(), Loterica.class, parametros);
	}

	@Override
	public Loterica findByCodigo(Long polo, Long id, Long dv) {
		return getSingleResultByNamedQuery(Loterica.NQ_SELECT_BY_POLO_ID_DV, polo, id, dv);
	}

	@Override
	public List<Object[]> findByCodUlDataArrecadacao(int codigoUL, Data inicio, Data fim) {
		String jpql = "SELECT aposta.modalidade,"

			+ "  count(aposta.id), sum(aposta.valor) + 0.0 "
			+ " From Aposta aposta "
			+ " join aposta.compra compra join compra.loterica loterica  join aposta.apostaComprada apostaComprada"

			+ " GROUP BY aposta.modalidade";

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("and loterica.id =", codigoUL);
		List<Long> convertToIdList = convertToIdList(Situacao.getSituacoesEfetivada());
		parametros.put("and apostaComprada.situacao.id in", convertToIdList);

		Data dataInicio = inicio.zereHoraMinutoSegundoMilisegundo();
		Data datafim = new Data(fim).add(Calendar.DAY_OF_MONTH, 1).zereHoraMinutoSegundoMilisegundo();

		parametros.put("and apostaComprada.dataFinalizacaoProcessamento >=", dataInicio);
		parametros.put("and apostaComprada.dataFinalizacaoProcessamento <", datafim);

		return getResultListByQuery(jpql, parametros);
	}

	@Override
	public List<Object[]> findByModalidadeDataArrecadacao(Modalidade modalidade, Data inicio, Data fim) {
		String jpql = "SELECT " 
				+" compra.loterica, " 
				+" count(aposta.id),  sum(aposta.valor) + 0.0 " 
				+" From Aposta aposta "
				+" join aposta.compra compra join compra.loterica loterica join aposta.apostaComprada apostaComprada"

				+" GROUP BY compra.loterica"; 

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("and aposta.modalidade =", modalidade);
		List<Long> convertToIdList = convertToIdList(Situacao.getSituacoesEfetivada());
		parametros.put("and apostaComprada.situacao.id in", convertToIdList);

		Data dataInicio = inicio.zereHoraMinutoSegundoMilisegundo();
		Data datafim = new Data(fim).add(Calendar.DAY_OF_MONTH, 1).zereHoraMinutoSegundoMilisegundo();

		parametros.put("and apostaComprada.dataFinalizacaoProcessamento >=", dataInicio);
		parametros.put("and apostaComprada.dataFinalizacaoProcessamento <", datafim);


		return getResultListByQuery(jpql, parametros);
	}



}
