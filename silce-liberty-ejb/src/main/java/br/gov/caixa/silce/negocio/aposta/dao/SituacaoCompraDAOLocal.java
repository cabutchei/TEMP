package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;

@Local
public interface SituacaoCompraDAOLocal extends SilceDAO<SituacaoCompra, Long> {

	List<SituacaoCompra> findByEnums(Situacao... situacoes);
}
