package br.gov.caixa.silce.dominio.servico.marketplace;

public class RetornoConfirmaReserva extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	public RetornoConfirmaReserva(String codCota, String nsbc) {
		this.codigoCota = codCota;
		this.nsbc = nsbc;
	}

	public RetornoConfirmaReserva(RetornoErro retornoErro) {
		super(retornoErro);
	}
}
