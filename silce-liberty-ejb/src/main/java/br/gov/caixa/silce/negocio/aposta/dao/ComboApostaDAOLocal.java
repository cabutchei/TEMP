package br.gov.caixa.silce.negocio.aposta.dao;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;

@Local
public interface ComboApostaDAOLocal extends SilceDAO<ComboAposta, Long> {

}
