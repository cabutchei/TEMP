package br.gov.caixa.silce.dominio.jogos;

import java.io.Serializable;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class Concurso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Modalidade modalidade;
	private final TipoConcurso tipoConcurso;
	private final Integer numero;
	private final Data dataFechamento;
	private final Data dataAbertura;
	private final Data dataSorteio;
	private final Data dataEncerramentoRevendaMkp;
	private final Decimal valorApostaMinima;
	private final SituacaoConcurso situacao;
	private final Boolean bloqueado;
	private final Data dataFinalBloqueio;
	private Decimal estimativa;
	
	public Concurso(Modalidade modalidade, TipoConcurso tipoConcurso, SituacaoConcurso situacao, Boolean bloqueado) {
		this(modalidade, tipoConcurso, null, null, null, null, null, situacao, bloqueado, null, null, null);
	}

	public Concurso(Modalidade modalidade, TipoConcurso tipoConcurso, Integer numero) {
		this(modalidade, tipoConcurso, numero, null, null, null, null, null, null, null, null, null);
	}

	public Concurso(Modalidade modalidade, TipoConcurso tipoConcurso, Integer numero, Data dataSorteio, Data dataEncerramentoRevendaMkp, Decimal estimativa, Boolean bloqueado) {
		this(modalidade, tipoConcurso, numero, null, null, dataSorteio, null, null, bloqueado, null, estimativa, dataEncerramentoRevendaMkp);
	}

	public Concurso(Modalidade modalidade, TipoConcurso tipoConcurso, Integer numero, Data dataFechamento, Data dataAbertura, Data dataSorteio, Decimal valorApostaMinima,
		SituacaoConcurso situacao, Boolean bloqueado, Data dataEncerramentoRevendaMkp) {
		this(modalidade, tipoConcurso, numero, dataFechamento, dataAbertura, dataSorteio, valorApostaMinima, situacao, bloqueado, null, null, dataEncerramentoRevendaMkp);
	}

	public Concurso(Modalidade modalidade, TipoConcurso tipoConcurso, Integer numero, Data dataFechamento, Data dataAbertura, Data dataSorteio, Decimal valorApostaMinima,
		SituacaoConcurso situacao, Boolean bloqueado, Data dataFinalBloqueio, Decimal estimativa, Data dataEncerramentoRevendaMkp) {
		this.modalidade = modalidade;
		this.tipoConcurso = tipoConcurso;
		this.numero = numero;
		this.dataFechamento = dataFechamento;
		this.dataAbertura = dataAbertura;
		this.dataSorteio = dataSorteio;
		this.valorApostaMinima = valorApostaMinima;
		this.situacao = situacao;
		this.bloqueado = bloqueado;
		this.dataFinalBloqueio = dataFinalBloqueio;
		this.estimativa = estimativa;
		this.dataEncerramentoRevendaMkp = dataEncerramentoRevendaMkp;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public Integer getNumero() {
		return numero;
	}

	public Data getDataFechamento() {
		// nova data para ser imutável
		if (dataFechamento != null) {
			return new Data(dataFechamento);
		}
		return null;
	}

	public Data getDataAbertura() {
		// nova data para ser imutável
		if (dataAbertura != null) {
			return new Data(dataAbertura);
		}
		return null;
	}

	public Decimal getValorApostaMinima() {
		return valorApostaMinima;
	}

	public Data getDataSorteio() {
		// nova data para ser imutável
		if (dataSorteio != null) {
			return new Data(dataSorteio);
		}
		return null;
	}

	public SituacaoConcurso getSituacao() {
		return situacao;
	}

	public boolean isAberto() {
		return SituacaoConcurso.ABERTO.equals(getSituacao());
	}

	public boolean isNaoInicializado() {
		return SituacaoConcurso.NAO_INICIALIZADO.equals(getSituacao());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append("Concurso [modalidade=").append(modalidade).append(", tipoConcurso=").append(tipoConcurso).append(", numero=")
				.append(numero).append(", dataFechamento=").append(dataFechamento).append(", dataAberturaProximoConcurso=")
				.append(dataAbertura).append(", dataSorteio=").append(dataSorteio).append(", dataEncerramentoRevendaMkp=").append(dataEncerramentoRevendaMkp)
				.append(", valorApostaMinima=").append(valorApostaMinima).append(", situacao=").append(situacao).append("]");
		return builder.toString();
	}

	public Boolean isBloqueado() {
		//prevenir nullpointer
		return Boolean.TRUE.equals(bloqueado);
	}

	public Data getDataFinalBloqueio() {
		return dataFinalBloqueio;
	}

	public Decimal getEstimativa() {
		return estimativa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bloqueado == null) ? 0 : bloqueado.hashCode());
		result = prime * result + ((dataAbertura == null) ? 0 : dataAbertura.hashCode());
		result = prime * result + ((dataFechamento == null) ? 0 : dataFechamento.hashCode());
		result = prime * result + ((dataFinalBloqueio == null) ? 0 : dataFinalBloqueio.hashCode());
		result = prime * result + ((dataSorteio == null) ? 0 : dataSorteio.hashCode());
		result = prime * result + ((estimativa == null) ? 0 : estimativa.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipoConcurso == null) ? 0 : tipoConcurso.hashCode());
		result = prime * result + ((valorApostaMinima == null) ? 0 : valorApostaMinima.hashCode());
		result = prime * result + ((dataEncerramentoRevendaMkp == null) ? 0 : dataEncerramentoRevendaMkp.hashCode());
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
		Concurso other = (Concurso) obj;
		if (bloqueado == null) {
			if (other.bloqueado != null) {
				return false;
			}
		} else if (!bloqueado.equals(other.bloqueado)) {
			return false;
		}
		if (dataAbertura == null) {
			if (other.dataAbertura != null) {
				return false;
			}
		} else if (!dataAbertura.equals(other.dataAbertura)) {
			return false;
		}
		if (dataFechamento == null) {
			if (other.dataFechamento != null) {
				return false;
			}
		} else if (!dataFechamento.equals(other.dataFechamento)) {
			return false;
		}
		if (dataFinalBloqueio == null) {
			if (other.dataFinalBloqueio != null) {
				return false;
			}
		} else if (!dataFinalBloqueio.equals(other.dataFinalBloqueio)) {
			return false;
		}
		if (dataSorteio == null) {
			if (other.dataSorteio != null) {
				return false;
			}
		} else if (!dataSorteio.equals(other.dataSorteio)) {
			return false;
		}
		if (dataEncerramentoRevendaMkp == null) {
			if (other.dataEncerramentoRevendaMkp != null) {
				return false;
			}
		} else if (!dataEncerramentoRevendaMkp.equals(other.dataEncerramentoRevendaMkp)) {
			return false;
		}
		if (estimativa == null) {
			if (other.estimativa != null) {
				return false;
			}
		} else if (!estimativa.equals(other.estimativa)) {
			return false;
		}
		if (modalidade != other.modalidade) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (situacao != other.situacao) {
			return false;
		}
		if (tipoConcurso != other.tipoConcurso) {
			return false;
		}
		if (valorApostaMinima == null) {
			if (other.valorApostaMinima != null) {
				return false;
			}
		} else if (!valorApostaMinima.equals(other.valorApostaMinima)) {
			return false;
		}
		return true;
	}

	public void setEstimativa(Decimal estimativa) {
		this.estimativa = estimativa;
	}

	public Data getDataEncerramentoRevendaMkp() {
		if (dataEncerramentoRevendaMkp != null) {
			return new Data(dataEncerramentoRevendaMkp);
		}
		return null;
	}
}
