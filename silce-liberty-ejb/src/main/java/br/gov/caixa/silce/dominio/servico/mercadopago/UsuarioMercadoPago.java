package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.io.Serializable;

public class UsuarioMercadoPago implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idApostador;
	private String primeiroNome;
	private String ultimoNome;
	private Integer ddd;
	private Integer telefone;
	private String siglaUf;
	private String cidade;
	private String cep;
	private String bairro;
	private boolean aceitaReceberNoticias = false;

	public Long getIdApostador() {
		return idApostador;
	}

	public void setIdApostador(Long idApostador) {
		this.idApostador = idApostador;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public boolean isAceitaReceberNoticias() {
		return aceitaReceberNoticias;
	}

	public void setAceitaReceberNoticias(boolean aceitaReceberNoticias) {
		this.aceitaReceberNoticias = aceitaReceberNoticias;
	}

}
