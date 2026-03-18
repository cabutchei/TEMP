package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;

@Local
public interface SituacaoApostaDAOLocal extends SilceDAO<SituacaoAposta, Long> {

	List<SituacaoAposta> findByEnums(Situacao... situacoes);
}
