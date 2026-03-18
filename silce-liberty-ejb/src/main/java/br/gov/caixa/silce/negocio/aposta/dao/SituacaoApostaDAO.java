package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;

@Stateless
public class SituacaoApostaDAO extends AbstractSilceDAO<SituacaoAposta, Long> implements SituacaoApostaDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SituacaoAposta> findByEnums(Situacao... situacoes) {
		List<Long> idSituacaoApostaList = convertToIdList(situacoes);
		return getResultListByNamedQuery(SituacaoAposta.NQ_FIND_BY_IDS, idSituacaoApostaList);
	}
}
