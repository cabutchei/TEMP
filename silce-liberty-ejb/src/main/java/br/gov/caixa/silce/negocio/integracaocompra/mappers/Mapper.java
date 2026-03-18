package br.gov.caixa.silce.negocio.integracaocompra.mappers;

import br.gov.caixa.dominio.AbstractEntidade;


public abstract class Mapper<N, E extends AbstractEntidade<?>> {

    public abstract E map(N nuvem);

    public abstract E map(N nuvem, E entidade);
    
}
