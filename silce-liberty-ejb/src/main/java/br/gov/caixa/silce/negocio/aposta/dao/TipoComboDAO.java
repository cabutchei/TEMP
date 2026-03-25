package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;

@Stateless
public class TipoComboDAO extends AbstractSilceDAO<TipoCombo, Long> implements TipoComboDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	public List<TipoCombo> findAllOrderByOrdem() {
		return getResultListByNamedQuery(TipoCombo.NQ_SELECT_COMBOS_ORDER_BY_ORDEM);
	}

}
