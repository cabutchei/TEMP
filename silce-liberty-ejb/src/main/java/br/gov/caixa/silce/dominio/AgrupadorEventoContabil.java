package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.ConversorUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Hora;

public class AgrupadorEventoContabil extends AbstractEntidade<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String dataFechamento;
	protected Integer produto;
	protected Integer concurso;
	protected Integer unidadeLoterica;
	protected Integer dvLoterica;
	protected Integer poloLoterica;
	protected String nomeFantasiaLoterica;

	public AgrupadorEventoContabil() {
		super();
	}

	public AgrupadorEventoContabil(String dataFechamento) {
		super();
		this.dataFechamento = dataFechamento;
	}

	public AgrupadorEventoContabil(String dataFechamento, Integer produto, Integer concurso, Integer unidadeLoterica, Integer dvLoterica, Integer poloLoterica,
		String nomeFantasiaLoterica) {
		super();
		this.dataFechamento = dataFechamento;
		this.produto = produto;
		this.concurso = concurso;
		this.unidadeLoterica = unidadeLoterica;
		this.dvLoterica = dvLoterica;
		this.poloLoterica = poloLoterica;
		this.nomeFantasiaLoterica = nomeFantasiaLoterica;
	}

	public String getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Integer getProduto() {
		return produto;
	}

	public void setProduto(Integer produto) {
		this.produto = produto;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Integer getUnidadeLoterica() {
		return unidadeLoterica;
	}

	public void setUnidadeLoterica(Integer unidadeLoterica) {
		this.unidadeLoterica = unidadeLoterica;
	}

	public String getDataFechamentoFormatada() {
		if (dataFechamento == null)
			return "";
		Data dataFromBase = DataUtil.stringToData(dataFechamento, "yyyy-MM-dd");
		return DataUtil.dataToString(dataFromBase, "dd/MM/yyyy");
	}

	public String getProdutoString() {
		return Modalidade.getByCodigo(produto).getDescricao();
	}

	public String getConcursoString() {
		return (concurso == null) ? "" : concurso.toString();
	}

	public String getUnidadeLotericaString() {
		return (unidadeLoterica == null) ? "" : unidadeLoterica.toString();
	}


	protected static BigDecimal plusValue(BigDecimal valuePlus) {
		return (valuePlus != null) ? valuePlus : new BigDecimal(0.0);
	}

	protected static String getTituloCsvFile(String tipoRelatorio) {
		String dataAtual = DataUtil.getDataAtual().toOnlyDataString();
		String horaAtual = new Hora().toString();
		String textoMKP = "";
		if (tipoRelatorio.equals("UL")) {
			textoMKP = "MKP ";
		}
		tipoRelatorio = (tipoRelatorio != null) ? "- " + tipoRelatorio : "";
		return "CAIXA ECONOMICA FEDERAL SILCE - SISTEMAS DE LOTERIAS EM CANAIS ELETRONICOS "
			+ dataAtual + " HR. BRASILIA " + horaAtual + " DEMONSTRATIVO DE EVENTOS CONTABEIS " + textoMKP
			+ tipoRelatorio + " #INTERNO.CONFIDENCIAL\n";
	}

	@Override
	public Long getId() {
		return (getDataFechamento() == null) ? null : getDataFechamentoLong();
	}

	@Override
	public void setId(Long id) {
	}

	private Long getDataFechamentoLong() {
		final String regex = "\\d+";
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(dataFechamento);
		return ConversorUtil.parseLong(matcher.group(0));
	}

	public Integer getDvLoterica() {
		return dvLoterica;
	}

	public void setDvLoterica(Integer dvLoterica) {
		this.dvLoterica = dvLoterica;
	}

	public Integer getPoloLoterica() {
		return poloLoterica;
	}

	public void setPoloLoterica(Integer poloLoterica) {
		this.poloLoterica = poloLoterica;
	}

	public String getNomeFantasiaLoterica() {
		return nomeFantasiaLoterica;
	}

	public void setNomeFantasiaLoterica(String nomeFantasiaLoterica) {
		this.nomeFantasiaLoterica = nomeFantasiaLoterica;
	}

}
