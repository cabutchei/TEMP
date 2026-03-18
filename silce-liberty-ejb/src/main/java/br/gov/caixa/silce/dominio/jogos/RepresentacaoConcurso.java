package br.gov.caixa.silce.dominio.jogos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.util.Validate;

public class RepresentacaoConcurso implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Modalidade modalidade;
	private final TipoConcurso tipoConcurso;
	private Subcanal subcanal;
	private Boolean indicadorBloqueioBolao;

	public static List<RepresentacaoConcurso> geraLista(Modalidade[] modalidades, TipoConcurso[] tiposConcurso, Subcanal[] subcanais, Boolean indicadorBloqueioBolao) {
		List<RepresentacaoConcurso> representacaoConcursos = new ArrayList<RepresentacaoConcurso>();
		Validate.notNull(subcanais, "Subcanal");
		Validate.notNull(modalidades, "Modalidade");
		Validate.notNull(tiposConcurso, "Tipo Concurso");

		for (Modalidade modalidade : modalidades) {
			for (TipoConcurso tipoConcurso : tiposConcurso) {
				if (!modalidade.temEspecial() && tipoConcurso.isEspecial()) {
					continue;
				}
				if (indicadorBloqueioBolao == null) {
					indicadorBloqueioBolao = false;
				}
				for (Subcanal subcanal : subcanais) {
					representacaoConcursos.add(new RepresentacaoConcurso(modalidade, tipoConcurso, subcanal, indicadorBloqueioBolao));
				}
			}
		}
		return representacaoConcursos;
	}

	public RepresentacaoConcurso(Modalidade modalidade, TipoConcurso tipoConcurso, Subcanal subcanal, Boolean indicadorBloqueioBolao) {
		this.modalidade = modalidade;
		this.tipoConcurso = tipoConcurso;
		this.subcanal = subcanal;
		this.indicadorBloqueioBolao = indicadorBloqueioBolao;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((subcanal == null) ? 0 : subcanal.hashCode());
		result = prime * result + ((tipoConcurso == null) ? 0 : tipoConcurso.hashCode());
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
		RepresentacaoConcurso other = (RepresentacaoConcurso) obj;
		if (modalidade != other.modalidade) {
			return false;
		}
		if (subcanal != other.subcanal) {
			return false;
		}
		if (tipoConcurso != other.tipoConcurso) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RepresentacaoConcurso [modalidade=" + modalidade.getDescricao() + ", tipoConcurso=" + tipoConcurso.getDescricao() + ", subcanal=" + subcanal.getNomeFormatado()
			+ "]";
	}

	public Boolean getIndicadorBloqueioBolao() {
		return indicadorBloqueioBolao;
	}

	public void setIndicadorBloqueioBolao(Boolean indicadorBloqueioBolao) {
		this.indicadorBloqueioBolao = indicadorBloqueioBolao;
	}
}
