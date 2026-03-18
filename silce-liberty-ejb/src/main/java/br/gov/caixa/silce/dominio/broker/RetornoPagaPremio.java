package br.gov.caixa.silce.dominio.broker;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.entidade.ConcursoPremiado;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.servico.sispl2.RetornoSISPL2PagaPremio;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.StringUtil;

public class RetornoPagaPremio extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	
	private static final String CODIGO_RETORNO_CANCELADO = completeCodigoRetorno("01");
	private static final String CODIGO_RETORNO_CONFIRMADO = completeCodigoRetorno("02");
	private static final String CODIGO_RETORNO_JA_CANCELADO	= completeCodigoRetorno("40");
	private static final String CODIGO_RETORNO_JA_CONFIRMADO = completeCodigoRetorno("50");
	public static final String CODIGO_RETORNO_JA_ESTA_EM_PAGAMENTO = completeCodigoRetorno("18");
	
	private static final Logger LOG = LogManager.getLogger(RetornoPagaPremio.class, new MessageFormatMessageFactory());

	private SituacaoApostaSISPL situacao;
	private NSB nsb;
	private NSB nsbTroca;
	private Premio premio;
	private Canal canalAposta;
	private OperacaoSispl operacaoSispl;
	private List<Canal> canaisPermitidos = new ArrayList<Canal>();

	private Modalidade modalidade;
	
	public static RetornoPagaPremio converteSemSituacao(RetornoSISPL2PagaPremio other) {
		RetornoPagaPremio retornoPagaPremio = new RetornoPagaPremio();
		retornoPagaPremio.setModalidade(other.getModalidade());
		retornoPagaPremio.setCanaisPermitidos(other.getCanaisPermitidos());
		retornoPagaPremio.setPremio(other.getPremio());
		retornoPagaPremio.setNsb(other.getNsb());
		retornoPagaPremio.setNsbTroca(other.getNsbTroca());
		retornoPagaPremio.setCanalAposta(other.getCanalPagador());
		return retornoPagaPremio;
	}

	public boolean isPagoSILCE() {
		return getPremio().isPagoSILCE();
	}

	public Integer getNumeroCanalPagamento() {
		return getPremio().getCanalPagamento();
	}

	public void setNumeroCanalPagamento(Integer numeroCanal) {
		getPremio().setCanalPagamento(numeroCanal);
	}

	public Integer getConcursoInicial() {
		return getPremio().getConcursoInicialTroca();
	}

	public void setConcursoInicial(Integer concursoInicial) {
		getPremio().setConcursoInicialTroca(concursoInicial);
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

	public Decimal getValorBruto() {
		return getPremio().getValorBruto();
	}

	public void setValorBruto(Decimal valorBruto) {
		getPremio().setValorBruto(valorBruto);
	}

	public Decimal getValorLiquido() {
		return getPremio().getValorLiquido();
	}

	public void setValorLiquido(Decimal valorLiquido) {
		getPremio().setValorLiquido(valorLiquido);
	}

	public Decimal getValorIRRF() {
		return getPremio().getValorIRRF();
	}

	public void setValorIRRF(Decimal valorIRRF) {
		getPremio().setValorIRRF(valorIRRF);
	}

	public NSB getNsb() {
		return nsb;
	}

	public void setNsb(NSB nsb) {
		this.nsb = nsb;
	}

	public NSB getNsbTroca() {
		return nsbTroca;
	}

	public void setNsbTroca(NSB nsbTroca) {
		this.nsbTroca = nsbTroca;
	}

	public SituacaoApostaSISPL getSituacao() {
		if (situacao == null && StringUtil.hasSomenteNumeros(getCodigoRetorno())) {
			situacao = SituacaoApostaSISPL.getByCodigo(Integer.valueOf(getCodigoRetorno()));
		}
		return situacao;
	}

	public void setSituacao(SituacaoApostaSISPL situacao) {
		this.situacao = situacao;
	}

	public Premio getPremio() {
		if(premio == null) {
			premio = new Premio();
		}
		return premio;
	}

	public Long getNsuPagamento() {
		return getPremio().getNsuPagamento();
	}

	public void setNsuPagamento(Long nsuPagamento) {
		getPremio().setNsuPagamento(nsuPagamento);
	}

	public Integer getConcursoInicialTroca() {
		return getPremio().getConcursoInicialTroca();
	}

	public void setConcursoInicialTroca(Integer concursoInicialTroca) {
		getPremio().setConcursoInicialTroca(concursoInicialTroca);
	}

	public List<ConcursoPremiado> getConcursosPremiados() {
		return getPremio().getConcursosPremiados();
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
	}

	public List<Canal> getCanaisPermitidos() {
		return canaisPermitidos;
	}

	public void setCanaisPermitidos(List<Canal> canaisPermitidos) {
		this.canaisPermitidos = canaisPermitidos;
	}

	public Canal getCanalAposta() {
		return canalAposta;
	}

	public void setCanalAposta(Canal canalAposta) {
		this.canalAposta = canalAposta;
	}

	public boolean isPremiado() {
		return SituacaoApostaSISPL.getSituacoesPremiadas().contains(getSituacao());
	}

	@Override
	public String toString() {
		return "RetornoPagaPremio [situacao=" + situacao + ", nsb=" + nsb + ", nsbTroca=" + nsbTroca +
			", numeroCanalPagamento=" + getNumeroCanalPagamento() +
			", operacaoSispl=" + getOperacaoSispl() +
			", codigoRetorno=" + getCodigoRetorno() +
			", codigoRetornoCompleto=" + getCodigoRetornoCompleto() +
			", mensagemRetorno=" + getMensagemRetorno() +
			", origemRetorno=" + getOrigemRetorno() +
			"]";
	}

	public OperacaoSispl getOperacaoSispl() {
		return operacaoSispl;
	}

	public void setOperacaoSispl(OperacaoSispl operacaoSispl) {
		this.operacaoSispl = operacaoSispl;
	}
	
	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		Boolean operacaoIgualAZero = super.isOperacaoExecutadaComSucesso();
		if (operacaoIgualAZero) {
			return Boolean.TRUE;
		} else {
			String codigoRetornoCompleto = getCodigoRetornoCompleto();
			if (OperacaoSispl.CANCELA_PAGAMENTO_PREMIO.equals(operacaoSispl)) {
				return CODIGO_RETORNO_JA_CANCELADO.equals(codigoRetornoCompleto) || CODIGO_RETORNO_CANCELADO.equals(codigoRetornoCompleto);
			} else if (OperacaoSispl.CONFIRMA_PAGAMENTO_PREMIO.equals(operacaoSispl)) {
				return CODIGO_RETORNO_JA_CONFIRMADO.equals(codigoRetornoCompleto) || CODIGO_RETORNO_CONFIRMADO.equals(codigoRetornoCompleto);
			}
		}
		return Boolean.FALSE;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;

	}

	public Modalidade getModalidade() {
		return modalidade;
	}
	
}
