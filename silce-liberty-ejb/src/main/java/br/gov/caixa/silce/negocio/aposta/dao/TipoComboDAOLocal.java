package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;

@Local
public interface TipoComboDAOLocal extends SilceDAO<TipoCombo, Long> {

	public List<TipoCombo> findAllOrderByOrdem();
}
