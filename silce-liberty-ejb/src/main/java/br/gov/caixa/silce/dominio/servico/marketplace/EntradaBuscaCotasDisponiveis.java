package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;

public final class EntradaBuscaCotasDisponiveis implements EntradaHttp<RetornoBuscaCotasDisponiveis> {

	private static final long serialVersionUID = 1L;

	private Integer idModalidade;
	private String modalidades;
	private Long numeroLoterico;
	private String numerosEscolhidos;
	private String numerosNaoEscolhidos;
	private Integer qtdDezenas;
	private Integer qtdMaximaCota;
	private Integer qtdMinimaCota;
	private Boolean sorteioHoje;
	private String valorMaximoCota;
	private String valorMinimoCota;
	private Integer tipoConcurso;
	private Integer tipoConsulta;
	private Integer idMunicipio;
	private Integer idUf;
	private Integer pagina;
	private Integer qtdPorPagina;

	public EntradaBuscaCotasDisponiveis(Integer idModalidade, Long numeroLoterico, String numerosEscolhidos, String numerosNaoEscolhidos, Integer qtdDezenas,
		Integer qtdMaximaCota, Integer qtdMinimaCota, Boolean sorteioHoje, String valorMaximoCota, String valorMinimoCota, Integer tipoConcurso, Integer tipoConsulta,
		Integer idMunicipio, Integer idUf, Integer pagina, Integer qtdPorPagina) {
		this.idModalidade = idModalidade;
		this.numeroLoterico = numeroLoterico;
		this.numerosEscolhidos = numerosEscolhidos;
		this.numerosNaoEscolhidos = numerosNaoEscolhidos;
		this.qtdDezenas = qtdDezenas;
		this.qtdMaximaCota = qtdMaximaCota;
		this.qtdMinimaCota = qtdMinimaCota;
		this.sorteioHoje = sorteioHoje;
		this.valorMaximoCota = valorMaximoCota;
		this.valorMinimoCota = valorMinimoCota;
		this.tipoConsulta = tipoConsulta;
		this.tipoConcurso = tipoConcurso;
		this.idMunicipio = idMunicipio;
		this.idUf = idUf;
		this.pagina = pagina;
		this.qtdPorPagina = qtdPorPagina;
	}

	public Integer getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Integer idModalidade) {
		this.idModalidade = idModalidade;
	}

	public Long getNumeroLoterico() {
		return numeroLoterico;
	}

	public void setNumeroLoterico(Long numeroLoterico) {
		this.numeroLoterico = numeroLoterico;
	}

	public String getNumerosEscolhidos() {
		return numerosEscolhidos;
	}

	public void setNumerosEscolhidos(String numerosEscolhidos) {
		this.numerosEscolhidos = numerosEscolhidos;
	}

	public String getNumerosNaoEscolhidos() {
		return numerosNaoEscolhidos;
	}

	public void setNumerosNaoEscolhidos(String numerosNaoEscolhidos) {
		this.numerosNaoEscolhidos = numerosNaoEscolhidos;
	}

	public Integer getQtdDezenas() {
		return qtdDezenas;
	}

	public void setQtdDezenas(Integer qtdDezenas) {
		this.qtdDezenas = qtdDezenas;
	}

	public Integer getQtdMaximaCota() {
		return qtdMaximaCota;
	}

	public void setQtdMaximaCota(Integer qtdMaximaCota) {
		this.qtdMaximaCota = qtdMaximaCota;
	}

	public Integer getQtdMinimaCota() {
		return qtdMinimaCota;
	}

	public void setQtdMinimaCota(Integer qtdMinimaCota) {
		this.qtdMinimaCota = qtdMinimaCota;
	}

	public Boolean getSorteioHoje() {
		return sorteioHoje;
	}

	public void setSorteioHoje(Boolean sorteioHoje) {
		this.sorteioHoje = sorteioHoje;
	}

	public String getValorMaximoCota() {
		return valorMaximoCota;
	}

	public void setValorMaximoCota(String valorMaximoCota) {
		this.valorMaximoCota = valorMaximoCota;
	}

	public String getValorMinimoCota() {
		return valorMinimoCota;
	}

	public void setValorMinimoCota(String valorMinimoCota) {
		this.valorMinimoCota = valorMinimoCota;
	}

	@Override
	public String toString() {
		return "EntradaBuscaCotasDisponiveis [modalidade=" + idModalidade + ", numeroLoterico=" + numeroLoterico + ", numerosEscolhidos=" + numerosEscolhidos
			+ ", numerosNaoEscolhido=" + numerosNaoEscolhidos + ", quantidadeDezena=" + qtdDezenas + ", quantidadeMaximaCota=" + qtdMaximaCota + ", quantidadeMinimaCota="
			+ qtdMinimaCota
			+ ", sorteadaHoje=" + sorteioHoje + ", valorMaximoCota=" + valorMaximoCota + ", valorMinimoCota=" + valorMinimoCota + "]";
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getIdUf() {
		return idUf;
	}

	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getQtdPorPagina() {
		return qtdPorPagina;
	}

	public void setQtdPorPagina(Integer qtdPorPagina) {
		this.qtdPorPagina = qtdPorPagina;
	}

	public Integer getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(Integer tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

}
