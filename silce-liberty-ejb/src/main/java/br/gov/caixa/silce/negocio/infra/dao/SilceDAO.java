package br.gov.caixa.silce.negocio.infra.dao;

import java.io.Serializable;

import org.apache.openjpa.datacache.CacheStatistics;
import org.apache.openjpa.kernel.QueryStatistics;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.negocio.infra.dao.DAO;

public interface SilceDAO<T extends AbstractEntidade<I>, I extends Serializable> extends DAO<T, I> {

	CacheStatistics recupereCacheStatistics();
	
	QueryStatistics<String> recupereQueryStatistics();
	
}
