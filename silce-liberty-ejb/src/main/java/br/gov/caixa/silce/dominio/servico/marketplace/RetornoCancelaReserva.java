package br.gov.caixa.silce.dominio.servico.marketplace;

public class RetornoCancelaReserva extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	public RetornoCancelaReserva() {
	}

	public RetornoCancelaReserva(RetornoErro retornoErro) {
		super(retornoErro);
	}
}
