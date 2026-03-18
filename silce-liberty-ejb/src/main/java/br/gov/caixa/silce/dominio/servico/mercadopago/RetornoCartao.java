package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.util.Calendar;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;

public class RetornoCartao extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private final Long idCartao;
	private final Long mesValidade;
	private final Long anoValidade;
	private final String primeirosDigitos;
	private final String ultimosDigitos;
	private final Long tamanhoCodigoSeguranca;
	private final String nomeMetodoPagamento;
	private final String thumbnail;
	private final String secureThumbnail;
	private final String idMetodoPagamento;

	public RetornoCartao(Long idCartao, Long mesValidade, Long anoValidade, String primeirosDigitos, String ultimosDigitos, Long tamanhoCodigoSeguranca,
		String idMetodoPagamento, String nomeMetodoPagamento, String thumbnail, String secureThumbnail) {
		this.idCartao = idCartao;
		this.mesValidade = mesValidade;
		this.anoValidade = anoValidade;
		this.primeirosDigitos = primeirosDigitos;
		this.ultimosDigitos = ultimosDigitos;
		this.tamanhoCodigoSeguranca = tamanhoCodigoSeguranca;
		this.idMetodoPagamento = idMetodoPagamento;
		this.nomeMetodoPagamento = nomeMetodoPagamento;
		this.thumbnail = thumbnail;
		this.secureThumbnail = secureThumbnail;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	/**
	 * @return the idCartao
	 */
	public Long getIdCartao() {
		return idCartao;
	}

	/**
	 * @return the mesValidade
	 */
	public Long getMesValidade() {
		return mesValidade;
	}

	/**
	 * @return the anoValidade
	 */
	public Long getAnoValidade() {
		return anoValidade;
	}

	/**
	 * @return the nomeMetodoPagamento
	 */
	public String getNomeMetodoPagamento() {
		return nomeMetodoPagamento;
	}

	/**
	 * @return the primeirosDigitos
	 */
	public String getPrimeirosDigitos() {
		return primeirosDigitos;
	}

	/**
	 * @return the ultimosDigitos
	 */
	public String getUltimosDigitos() {
		return ultimosDigitos;
	}

	/**
	 * @return the tamanhoCodigoSeguranca
	 */
	public Long getTamanhoCodigoSeguranca() {
		return tamanhoCodigoSeguranca;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @return the secureThumbnail
	 */
	public String getSecureThumbnail() {
		return secureThumbnail;
	}

	/**
	 * @return the idMetodoPagamento
	 */
	public String getIdMetodoPagamento() {
		return idMetodoPagamento;
	}

	public boolean isValido() {
		return idCartao != null && primeirosDigitos != null
			&& ultimosDigitos != null && tamanhoCodigoSeguranca != null && nomeMetodoPagamento != null
			&& thumbnail != null && secureThumbnail != null && idMetodoPagamento != null && !isExpirado();
	}

	public boolean isExpirado() {
		Data dataAtual = DataUtil.getDataAtual();
		Data dataExpiracao = new Data(anoValidade.intValue(), mesValidade.intValue() - 1, dataAtual.get(Calendar.DAY_OF_MONTH));
		dataExpiracao = new Data(dataExpiracao.getYear(), dataExpiracao.getMonth() - 1, dataExpiracao.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH));
		return mesValidade != null && anoValidade != null
			&& dataExpiracao.before(dataAtual);
	}
}
