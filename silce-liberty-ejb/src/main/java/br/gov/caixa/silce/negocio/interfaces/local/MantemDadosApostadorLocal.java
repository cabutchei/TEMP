package br.gov.caixa.silce.negocio.interfaces.local;

import javax.ejb.Local;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.ApostadorVO;
import br.gov.caixa.silce.negocio.interfaces.MantemDadosApostador;
import br.gov.caixa.util.Data;

 
@Local
public interface MantemDadosApostadorLocal  extends MantemDadosApostador {

	Apostador createApostadorFromVO(ApostadorVO apostadorVO) throws NegocioException;

	public void altereDataFimBloqueioVinculacaoCarteira(Long idApostador, Data novaData) throws NegocioException;

	void altereMercadoPagoCustomerId(Long idApostador, String mercadopagoCustomerId);

}
