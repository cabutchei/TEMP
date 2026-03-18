package br.gov.caixa.silce.negocio.aposta.dao;

import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;

@Stateless
public class ComboApostaDAO extends AbstractSilceDAO<ComboAposta, Long> implements ComboApostaDAOLocal {
	private static final long serialVersionUID = 1L;
}
