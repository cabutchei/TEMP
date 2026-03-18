package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;

@Stateless
public class SituacaoCompraDAO extends AbstractSilceDAO<SituacaoCompra, Long> implements SituacaoCompraDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SituacaoCompra> findByEnums(Situacao... situacoes) {
		List<Long> idSituacaoCompraList = convertToIdList(situacoes);
		return getResultListByNamedQuery(SituacaoCompra.NQ_FIND_BY_IDS, idSituacaoCompraList);
	}
}
