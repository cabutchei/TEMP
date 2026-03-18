package br.gov.caixa.silce.negocio.aposta.dao;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;

@Local
public interface SituacaoReservaCotaBolaoDAOLocal extends SilceDAO<SituacaoReservaCotaBolao, Long> {
}
