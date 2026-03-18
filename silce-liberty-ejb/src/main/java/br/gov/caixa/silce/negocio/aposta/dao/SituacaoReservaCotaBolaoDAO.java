package br.gov.caixa.silce.negocio.aposta.dao;

import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;

@Stateless
public class SituacaoReservaCotaBolaoDAO extends AbstractSilceDAO<SituacaoReservaCotaBolao, Long> implements SituacaoReservaCotaBolaoDAOLocal {

	private static final long serialVersionUID = 1L;
}
