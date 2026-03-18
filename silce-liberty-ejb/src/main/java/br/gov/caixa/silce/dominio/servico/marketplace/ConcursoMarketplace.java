package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConcursoMarketplace implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer concurso;
	private Integer modalidade;
	private Integer tipoConcurso;
	private String dataHoraSorteio;
	private String dataHoraEncerramentoRevenda;
	private BigDecimal valorMenorCota;
	private BigDecimal valorEstimativaPremio;

	public ConcursoMarketplace() {

	}

	public ConcursoMarketplace(Integer concurso, Integer modalidade, Integer tipoConcurso, String dataHoraSorteio, String dataHoraEncerramentoRevenda,
		BigDecimal valorMenorCota, BigDecimal valorEstimativaPremio) {
		super();
		this.concurso = concurso;
		this.modalidade = modalidade;
		this.tipoConcurso = tipoConcurso;
		this.dataHoraSorteio = dataHoraSorteio;
		this.dataHoraEncerramentoRevenda = dataHoraEncerramentoRevenda;
		this.valorMenorCota = valorMenorCota;
		this.valorEstimativaPremio = valorEstimativaPremio;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Integer getModalidade() {
		return modalidade;
	}

	public void setModalidade(Integer modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(Integer tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public String getDataHoraSorteio() {
		return dataHoraSorteio;
	}

	public void setDataHoraSorteio(String dataHoraSorteio) {
		this.dataHoraSorteio = dataHoraSorteio;
	}

	public String getDataHoraEncerramentoRevenda() {
		return dataHoraEncerramentoRevenda;
	}

	public void setDataHoraEncerramentoRevenda(String dataHoraEncerramentoRevenda) {
		this.dataHoraEncerramentoRevenda = dataHoraEncerramentoRevenda;
	}

	public BigDecimal getValorMenorCota() {
		return valorMenorCota;
	}

	public void setValorMenorCota(BigDecimal valorMenorCota) {
		this.valorMenorCota = valorMenorCota;
	}

	public BigDecimal getValorEstimativaPremio() {
		return valorEstimativaPremio;
	}

	public void setValorEstimativaPremio(BigDecimal valorEstimativaPremio) {
		this.valorEstimativaPremio = valorEstimativaPremio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((dataHoraEncerramentoRevenda == null) ? 0 : dataHoraEncerramentoRevenda.hashCode());
		result = prime * result + ((dataHoraSorteio == null) ? 0 : dataHoraSorteio.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((tipoConcurso == null) ? 0 : tipoConcurso.hashCode());
		result = prime * result + ((valorMenorCota == null) ? 0 : valorMenorCota.hashCode());
		result = prime * result + ((valorEstimativaPremio == null) ? 0 : valorEstimativaPremio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConcursoMarketplace other = (ConcursoMarketplace) obj;
		if (concurso == null) {
			if (other.concurso != null)
				return false;
		} else if (!concurso.equals(other.concurso))
			return false;
		if (dataHoraEncerramentoRevenda == null) {
			if (other.dataHoraEncerramentoRevenda != null)
				return false;
		} else if (!dataHoraEncerramentoRevenda.equals(other.dataHoraEncerramentoRevenda))
			return false;
		if (dataHoraSorteio == null) {
			if (other.dataHoraSorteio != null)
				return false;
		} else if (!dataHoraSorteio.equals(other.dataHoraSorteio))
			return false;
		if (modalidade == null) {
			if (other.modalidade != null)
				return false;
		} else if (!modalidade.equals(other.modalidade))
			return false;
		if (tipoConcurso == null) {
			if (other.tipoConcurso != null)
				return false;
		} else if (!tipoConcurso.equals(other.tipoConcurso))
			return false;
		if (valorMenorCota == null) {
			if (other.valorMenorCota != null)
				return false;
		} else if (!valorMenorCota.equals(other.valorMenorCota))
			return false;
		if (valorEstimativaPremio == null) {
			if (other.valorEstimativaPremio != null)
				return false;
		} else if (!valorEstimativaPremio.equals(other.valorEstimativaPremio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConcursoMarketplace [concurso=" + concurso + ", modalidade=" + modalidade + ", tipoConcurso=" + tipoConcurso + ", dataHoraSorteio=" + dataHoraSorteio
			+ ", dataHoraEncerramentoRevenda=" + dataHoraEncerramentoRevenda + "valorMenorCota = " + valorMenorCota + ", valorEstimativaPremio=" + valorEstimativaPremio + "]";
	}

}
