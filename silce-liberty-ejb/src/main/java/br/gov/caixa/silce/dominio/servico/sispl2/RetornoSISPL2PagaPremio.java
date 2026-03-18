package br.gov.caixa.silce.dominio.servico.sispl2;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

public class RetornoSISPL2PagaPremio extends SaidaHttpSISPL2 {

	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_CODE_CANCELA_PAGAMENTO_VALIDO = 404;
	public static final String CODIGO_ERRO_CANCELA_PAGAMENTO_VALIDO = "NAO_EXISTE_AUTORIZACAO_PAGAMENTO_PREMIO";

	private SituacaoApostaSISPL2 situacao;
	private Modalidade modalidade;
	private Decimal valorPremioBruto;
	private NSB nsb;
	private NSB nsbTroca;
	private Decimal valorIR;
	private Decimal valorLiquido;
	private Premio premio;
	private String situacaoConcurso;
	private List<Canal> canaisPermitidos = new ArrayList<Canal>();
	private Canal canalPagador;

	public RetornoSISPL2PagaPremio(SituacaoApostaSISPL2 situacao, Modalidade modalidade, Decimal valorPremioBruto, NSB nsb, Decimal valorIR, Decimal valorLiquido, Premio premio,
		String situacaoConcurso, List<Canal> canaisPermitidos, NSB nsbTroca) {
		this(situacao, modalidade, valorPremioBruto, nsb, valorIR, valorLiquido, premio, situacaoConcurso, canaisPermitidos, nsbTroca, null);
	}

	public RetornoSISPL2PagaPremio(SituacaoApostaSISPL2 situacao, Modalidade modalidade, Decimal valorPremioBruto, NSB nsb, Decimal valorIR, Decimal valorLiquido,
		Premio premio,
		String situacaoConcurso, List<Canal> canaisPermitidos, NSB nsbTroca, Canal canalPagador) {
		this.situacao = situacao;
		this.modalidade = modalidade;
		this.valorPremioBruto = valorPremioBruto;
		this.valorIR = valorIR;
		this.valorLiquido = valorLiquido;
		this.premio = premio;
		this.nsb = nsb;
		this.situacaoConcurso = situacaoConcurso;
		this.nsbTroca = nsbTroca;
		this.canaisPermitidos = canaisPermitidos;
		this.canalPagador = canalPagador;
	}

	public RetornoSISPL2PagaPremio(SituacaoApostaSISPL2 situacao, Modalidade modalidade, Decimal valorPremioBruto, Decimal valorIR, Decimal valorLiquido, Premio premio,
		String situacaoConcurso, List<Canal> canaisPermitidos, NSB nsbTroca) {
		this(situacao, modalidade, valorPremioBruto, null, valorIR, valorLiquido, premio, situacaoConcurso, canaisPermitidos, nsbTroca);
	}

	public RetornoSISPL2PagaPremio(SituacaoApostaSISPL2 situacao, Modalidade modalidade, Decimal valorPremioBruto, Decimal valorIR, Decimal valorLiquido, Premio premio,
		String situacaoConcurso, List<Canal> canaisPermitidos, NSB nsbTroca, Canal canalPagador) {
		this(situacao, modalidade, valorPremioBruto, null, valorIR, valorLiquido, premio, situacaoConcurso, canaisPermitidos, nsbTroca, canalPagador);
	}

	public RetornoSISPL2PagaPremio() {
		// Construtor Padrão
	}

	public Premio getPremio() {
		if (premio == null) {
			premio = new Premio();
		}
		return premio;
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Decimal getValorIR() {
		return valorIR;
	}

	public void setValorIR(Decimal valorIR) {
		this.valorIR = valorIR;
	}

	public Decimal getValorPremioBruto() {
		return valorPremioBruto;
	}

	public void setValorPremioBruto(Decimal valorPremioBruto) {
		this.valorPremioBruto = valorPremioBruto;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public SituacaoApostaSISPL2 getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoApostaSISPL2 situacao) {
		this.situacao = situacao;
	}

	public NSB getNsb() {
		return nsb;
	}

	public void setNsb(NSB nsb) {
		this.nsb = nsb;
	}

	public boolean isPagoSILCE() {
		return getPremio().isPagoSILCE();
	}

	public String getSituacaoConcurso() {
		return situacaoConcurso;
	}

	public void setSituacaoConcurso(String situacaoConcurso) {
		this.situacaoConcurso = situacaoConcurso;
	}

	public Integer getNumeroCanalPagamento() {
		return getPremio().getCanalPagamento();
	}

	public Integer getConcursoInicial() {
		return getPremio().getConcursoInicialTroca();
	}

	public void setConcursoInicial(Integer concursoInicial) {
		getPremio().setConcursoInicialTroca(concursoInicial);
	}

	public Long getNsuPagamento() {
		return getPremio().getNsuPagamento();
	}

	public void setNsuPagamento(Long nsuPagamento) {
		getPremio().setNsuPagamento(nsuPagamento);
	}

	public Data getDataPagamentoPremio() {
		return getPremio().getDataPagamento();
	}

	public void setDataPagamentoPremio(Data dataPagamentoPremio) {
		getPremio().setDataPagamento(dataPagamentoPremio);
	}

	public Hora getHoraPagamentoPremio() {
		return getPremio().getHoraPagamento();
	}

	public void setHoraPagamentoPremio(Hora horaPagamentoPremio) {
		getPremio().setHoraPagamento(horaPagamentoPremio);
	}

	public NSB getNsbTroca() {
		return nsbTroca;
	}

	public void setNsbTroca(NSB nsbTroca) {
		this.nsbTroca = nsbTroca;
	}

	public List<Canal> getCanaisPermitidos() {
		return canaisPermitidos;
	}

	public void setCanaisPermitidos(List<Canal> canaisPermitidos) {
		this.canaisPermitidos = canaisPermitidos;
	}

	public boolean isPremiado() {
		return SituacaoApostaSISPL2.getSituacoesPremiadas().contains(getSituacao());
	}

	public Canal getCanalPagador() {
		return canalPagador;
	}

	public void setCanalPagador(Canal canalPagador) {
		this.canalPagador = canalPagador;
	}
}
