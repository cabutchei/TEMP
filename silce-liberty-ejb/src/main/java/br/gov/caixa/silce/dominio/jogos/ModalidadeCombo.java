package br.gov.caixa.silce.dominio.jogos;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMaisMilionaria;

/**
 * Modalidade que está disponível para apostar neste combo.
 * 
 * @author f579942
 *
 */
public class ModalidadeCombo implements Serializable, Comparable<ModalidadeCombo> {

	private static final long serialVersionUID = 1L;

	private final AbstractParametroJogo parametro;
	private Integer quantidadeApostas;
	private Integer quantidadeTeimosinhas;
	private Integer quantidadeDezenas;
	private Integer quantidadeDezenasTrevos;

	public ModalidadeCombo(AbstractParametroJogo parametro) {
		this.parametro = parametro;
		this.quantidadeApostas = 1;
		this.quantidadeTeimosinhas = 0;
		if (parametro instanceof ParametroJogoNumerico) {
			this.quantidadeDezenas = ((ParametroJogoNumerico) parametro).getQuantidadeMinima();
		}

		if (parametro instanceof ParametroMaisMilionaria) {
			this.quantidadeDezenas = ((ParametroMaisMilionaria) parametro).getQuantidadeMinima();
			this.quantidadeDezenasTrevos = ((ParametroMaisMilionaria) parametro).getTrevos().getQuantidadeMinimaTrevo();
		}
	}

	public ModalidadeCombo(AbstractParametroJogo parametro, Integer quantidadeApostas) {
		this(parametro);
		this.quantidadeApostas = quantidadeApostas;
		this.quantidadeTeimosinhas = 0;
	}

	public ModalidadeCombo(AbstractParametroJogo parametro, Integer quantidadeApostas, Integer quantidadeTeimosinhas) {
		this(parametro, quantidadeApostas);
		this.quantidadeTeimosinhas = quantidadeTeimosinhas;
	}

	public ModalidadeCombo(AbstractParametroJogo parametro, Integer quantidadeApostas, Integer quantidadeTeimosinhas, Integer quantidadeDezenas) {
		this(parametro, quantidadeApostas, quantidadeTeimosinhas);
		this.quantidadeDezenas = quantidadeDezenas;
	}

	public ModalidadeCombo(AbstractParametroJogo parametro, Integer quantidadeApostas, Integer quantidadeTeimosinhas, Integer quantidadeDezenas, Integer quantidadeDezenasTrevo) {
		this(parametro, quantidadeApostas, quantidadeTeimosinhas);
		this.quantidadeDezenas = quantidadeDezenas;
		this.quantidadeDezenasTrevos = quantidadeDezenasTrevo;
	}

	public Modalidade getModalidade() {
		if (parametro != null && parametro.getConcurso() != null) {
			return parametro.getConcurso().getModalidade();
		}
		return null;
	}

	public TipoConcurso getTipoConcurso() {
		if (parametro != null && parametro.getConcurso() != null) {
			return parametro.getConcurso().getTipoConcurso();
		}
		return null;
	}

	public Integer getQuantidadeApostas() {
		return quantidadeApostas;
	}

	public void setQuantidadeApostas(Integer quantidadeApostas) {
		this.quantidadeApostas = quantidadeApostas;
	}

	public Concurso getConcurso() {
		if (parametro != null && parametro.getConcurso() != null) {
			return parametro.getConcurso();
		}
		return null;
	}

	public AbstractParametroJogo getParametro() {
		return parametro;
	}

	public Integer getQuantidadeTeimosinhas() {
		return quantidadeTeimosinhas;
	}

	public void setQuantidadeTeimosinhas(Integer quantidadeTeimosinhas) {
		this.quantidadeTeimosinhas = quantidadeTeimosinhas;
	}

	@Override
	public int compareTo(ModalidadeCombo o) {
		return this.getParametro().compareTo(o.getParametro());
	}

	public Integer getQuantidadeDezenas() {
		return quantidadeDezenas;
	}

	public void setQuantidadeDezenas(Integer quantidadeDezenas) {
		this.quantidadeDezenas = quantidadeDezenas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parametro == null) ? 0 : parametro.hashCode());
		result = prime * result + ((quantidadeApostas == null) ? 0 : quantidadeApostas.hashCode());
		result = prime * result + ((quantidadeDezenas == null) ? 0 : quantidadeDezenas.hashCode());
		result = prime * result + ((quantidadeTeimosinhas == null) ? 0 : quantidadeTeimosinhas.hashCode());
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
		ModalidadeCombo other = (ModalidadeCombo) obj;
		if (parametro == null) {
			if (other.parametro != null) {
				return false;
			}
		} else if (!parametro.equals(other.parametro)) {
			return false;
		}
		if (quantidadeApostas == null) {
			if (other.quantidadeApostas != null) {
				return false;
			}
		} else if (!quantidadeApostas.equals(other.quantidadeApostas)) {
			return false;
		}
		if (quantidadeDezenas == null) {
			if (other.quantidadeDezenas != null) {
				return false;
			}
		} else if (!quantidadeDezenas.equals(other.quantidadeDezenas)) {
			return false;
		}
		if (quantidadeTeimosinhas == null) {
			if (other.quantidadeTeimosinhas != null) {
				return false;
			}
		} else if (!quantidadeTeimosinhas.equals(other.quantidadeTeimosinhas)) {
			return false;
		}
		return true;
	}

	public Integer getQuantidadeDezenasTrevos() {
		return quantidadeDezenasTrevos;
	}

	public void setQuantidadeDezenasTrevos(Integer quantidadeDezenasTrevos) {
		this.quantidadeDezenasTrevos = quantidadeDezenasTrevos;
	}

}
