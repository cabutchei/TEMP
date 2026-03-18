package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.Arrays;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo.SituacaoConcursoCanal;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.SituacaoConcurso;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.util.AbstractCacheKey;

/**
 * @author r679109
 *
 */
public class ParametroJogos<T extends RetornoParametrosJogos> extends AbstractCacheKey implements EntradaBroker<T> {
	
	private static final long serialVersionUID = 1L;
	private TipoConcurso tipoConcurso;
	private Modalidade modalidade;
	private int qtdRetorno;
	private boolean ordenacaoDataAberturaDesc;
	private SituacaoConcurso[] situacoesConcurso;
	private SituacaoConcursoCanal situacaoConcursoCanal;
	private Integer concurso;
	
	public ParametroJogos(Modalidade modalidade, Integer concurso) {
		this(null, modalidade, 0, false, concurso, null);
	}

	public ParametroJogos(TipoConcurso tipoConcurso, Modalidade modalidade, int qtdRetorno, boolean ordenacaoDataAberturaDesc,
			SituacaoConcursoCanal situacaoConcursoCanal, SituacaoConcurso... situacao) {
		this(tipoConcurso, modalidade, qtdRetorno, ordenacaoDataAberturaDesc, null, situacaoConcursoCanal, situacao);
	}

	public ParametroJogos(TipoConcurso tipoConcurso, Modalidade modalidade, int qtdRetorno, boolean ordenacaoDataAberturaDesc,
		Integer concurso, SituacaoConcursoCanal situacaoConcursoCanal, SituacaoConcurso... situacao) {
		this.tipoConcurso = tipoConcurso;
		this.modalidade = modalidade;
		this.qtdRetorno = qtdRetorno;
		this.ordenacaoDataAberturaDesc = ordenacaoDataAberturaDesc;
		this.concurso = concurso;
		this.situacoesConcurso = situacao;
		this.situacaoConcursoCanal = situacaoConcursoCanal;
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(TipoConcurso tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public int getQtdRetorno() {
		return qtdRetorno;
	}

	public void setQtdRetorno(int qtdRetorno) {
		this.qtdRetorno = qtdRetorno;
	}

	public boolean isOrdenacaoDataAberturaDesc() {
		return ordenacaoDataAberturaDesc;
	}

	public void setOrdenacaoDataAberturaDesc(boolean ordenacaoDataAberturaDesc) {
		this.ordenacaoDataAberturaDesc = ordenacaoDataAberturaDesc;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public SituacaoConcurso[] getSituacoesConcurso() {
		return situacoesConcurso;
	}

	public void setSituacoesConcurso(SituacaoConcurso[] situacoesConcurso) {
		this.situacoesConcurso = situacoesConcurso;
	}
	
	public SituacaoConcursoCanal getSituacaoConcursoCanal() {
		return situacaoConcursoCanal;
	}

	public void setSituacaoConcursoCanal(SituacaoConcursoCanal situacaoConcursoCanal) {
		this.situacaoConcursoCanal = situacaoConcursoCanal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + (ordenacaoDataAberturaDesc ? 1231 : 1237);
		result = prime * result + qtdRetorno;
		result = prime * result + ((situacaoConcursoCanal == null) ? 0 : situacaoConcursoCanal.hashCode());
		result = prime * result + Arrays.hashCode(situacoesConcurso);
		result = prime * result + ((tipoConcurso == null) ? 0 : tipoConcurso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ParametroJogos)) {
			return false;
		}
		ParametroJogos<?> other = (ParametroJogos<?>) obj;
		if (concurso == null) {
			if (other.concurso != null) {
				return false;
			}
		} else if (!concurso.equals(other.concurso)) {
			return false;
		}
		if (modalidade != other.modalidade) {
			return false;
		}
		if (ordenacaoDataAberturaDesc != other.ordenacaoDataAberturaDesc) {
			return false;
		}
		if (qtdRetorno != other.qtdRetorno) {
			return false;
		}
		if (situacaoConcursoCanal != other.situacaoConcursoCanal) {
			return false;
		}
		if (!Arrays.equals(situacoesConcurso, other.situacoesConcurso)) {
			return false;
		}
		if (tipoConcurso != other.tipoConcurso) {
			return false;
		}
		return true;
	}

}
