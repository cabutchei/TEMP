package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.jogos.Concurso;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.EnumUtil;

/**
 * @author c101482
 *
 */
public abstract class AbstractParametroJogo extends SaidaBroker implements Comparable<AbstractParametroJogo> {

	public enum SituacaoConcursoCanal implements CaixaEnum<Integer> {

		ABERTO(0), FECHADO(1);

		private final Integer value;

		private SituacaoConcursoCanal(int value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

		public static SituacaoConcursoCanal getByValue(Integer value) {
			return EnumUtil.recupereByValue(values(), value);
		}

	}

	private static final long serialVersionUID = 1L;
	
	private Concurso concurso;

	private SituacaoConcursoCanal situacaoConcursoCanal;

	protected boolean marketplace = false;

	public boolean isConcursoDisponivelSimulacao() {
		return isConcursoDisponivelSimulacao(null);
	}
	
	public boolean isConcursoDisponivelSimulacao(AbstractParametroJogo parametroEspecial) {
		boolean escondidoPeloEspecial = false;
		if (parametroEspecial != null && parametroEspecial.isConcursoDisponivelSimulacao() && parametroEspecial.getConcurso().getTipoConcurso() == TipoConcurso.ESPECIAL) {
			escondidoPeloEspecial = getConcurso().getNumero() >= parametroEspecial.getConcurso().getNumero();
		}
		return !escondidoPeloEspecial && getConcurso() != null && (getConcurso().isNaoInicializado() || isDisponivelEfetivacao());
	}

	public final Concurso getConcurso() {
		return concurso;
	}

	public final void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public final SituacaoConcursoCanal getSituacaoConcursoCanal() {
		return situacaoConcursoCanal;
	}

	public final void setSituacaoConcursoCanal(SituacaoConcursoCanal situacaoConcursoCanal) {
		this.situacaoConcursoCanal = situacaoConcursoCanal;
	}

	public final boolean getMarketplace() {
		return marketplace;
	}

	public final void setMarketplace(boolean marketplace) {
		this.marketplace = marketplace;
	}

	public boolean isConcursoCanalAberto() {
		return SituacaoConcursoCanal.ABERTO.equals(getSituacaoConcursoCanal());
	}

	public boolean isDisponivelEfetivacao() {
		return isConcursoCanalAberto() && concurso.isAberto() && !isDataFechamentoPassou(concurso.getDataFechamento()) && !concurso.isBloqueado();
	}

	public boolean isDisponivelRevendaBolao() {
		return !isDataFechamentoPassou(concurso.getDataEncerramentoRevendaMkp()) && !concurso.isBloqueado();
	}

	private boolean isDataFechamentoPassou(Data dataFechamento) {
		Data timestampAtual = DataUtil.getTimestampAtual();
		return dataFechamento.before(timestampAtual);
	}

	@Override
	public int compareTo(AbstractParametroJogo o) {
		int compareTo2 = o.getConcurso().getTipoConcurso().getCodigo().compareTo(this.getConcurso().getTipoConcurso().getCodigo());
		if (compareTo2 == 0) {
			return this.getConcurso().getModalidade().getPosicao().compareTo(o.getConcurso().getModalidade().getPosicao());
		}
		return compareTo2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((situacaoConcursoCanal == null) ? 0 : situacaoConcursoCanal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractParametroJogo other = (AbstractParametroJogo) obj;
		if (concurso == null) {
			if (other.concurso != null) {
				return false;
			}
		} else if (!concurso.equals(other.concurso)) {
			return false;
		}
		if (situacaoConcursoCanal != other.situacaoConcursoCanal) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AbstractParametroJogo [marketplace=" + marketplace + ", situacaoConcursoCanal=" + situacaoConcursoCanal + ", concurso=" + concurso + "]";
	}

}
