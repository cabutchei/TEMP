package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.sql.Time;

import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoPremio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Decimal;

public class PendenciaPreiomios implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dataFinalizacaoCompra;
	private Time horaFinalizacaoCompra;
	private Modalidade modalidade;
	private Decimal valorAposta;
	private Integer numeroTransacaoCompra;
	private Long numeroAposta;
	private SituacaoAposta.Situacao situacaoAposta;
	private Long numeroApostaComprada;
	private Long numeroPremio;
	private SituacaoPremio.Situacao situacaoPremio;
	private Long numeroCompra;
	private SituacaoCompra.Situacao situacaoCompra;

	public String getDataFinalizacaoCompra() {
		return dataFinalizacaoCompra;
	}

	public void setDataFinalizacaoCompra(String dataFinalizacaoCompra) {
		this.dataFinalizacaoCompra = dataFinalizacaoCompra;
	}

	public Time getHoraFinalizacaoCompra() {
		return horaFinalizacaoCompra;
	}

	public void setHoraFinalizacaoCompra(Time horaFinalizacaoCompra) {
		this.horaFinalizacaoCompra = horaFinalizacaoCompra;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Decimal getValorAposta() {
		return valorAposta;
	}

	public void setValorAposta(Decimal valorAposta) {
		this.valorAposta = valorAposta;
	}

	public Integer getNumeroTransacaoCompra() {
		return numeroTransacaoCompra;
	}

	public void setNumeroTransacaoCompra(Integer numeroTransacaoCompra) {
		this.numeroTransacaoCompra = numeroTransacaoCompra;
	}

	public Long getNumeroAposta() {
		return numeroAposta;
	}

	public void setNumeroAposta(Long numeroAposta) {
		this.numeroAposta = numeroAposta;
	}

	public SituacaoAposta.Situacao getSituacaoAposta() {
		return situacaoAposta;
	}

	public void setSituacaoAposta(SituacaoAposta.Situacao situacao) {
		this.situacaoAposta = situacao;
	}

	public Long getNumeroApostaComprada() {
		return numeroApostaComprada;
	}

	public void setNumeroApostaComprada(Long numeroApostaComprada) {
		this.numeroApostaComprada = numeroApostaComprada;
	}

	public Long getNumeroPremio() {
		return numeroPremio;
	}

	public void setNumeroPremio(Long numeroPremio) {
		this.numeroPremio = numeroPremio;
	}

	public SituacaoPremio.Situacao getSituacaoPremio() {
		return situacaoPremio;
	}

	public void setSituacaoPremio(SituacaoPremio.Situacao situacaoPremio) {
		this.situacaoPremio = situacaoPremio;
	}

	public Long getNumeroCompra() {
		return numeroCompra;
	}

	public void setNumeroCompra(Long numeroCompra) {
		this.numeroCompra = numeroCompra;
	}

	public SituacaoCompra.Situacao getSituacaoCompra() {
		return situacaoCompra;
	}

	public void setSituacaoCompra(SituacaoCompra.Situacao situacaoCompra) {
		this.situacaoCompra = situacaoCompra;
	}

}
