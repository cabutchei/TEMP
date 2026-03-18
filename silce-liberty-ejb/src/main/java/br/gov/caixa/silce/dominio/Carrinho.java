package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.AbstractApostaVO;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.util.Decimal;

public class Carrinho implements Serializable {

	private static final long serialVersionUID = 1L;

	private Decimal valorTotal = Decimal.ZERO;

	private List<Aposta<?>> apostas = new ArrayList<Aposta<?>>();

	// TODO QUARKUS
	private Long idCarrinho = (long) 0;

	public Carrinho(Decimal valorTotal, List<Aposta<?>> apostas) {
		this.apostas = Collections.unmodifiableList(apostas);
		this.valorTotal = valorTotal;
	}

	// TODO QUARKUS
	public Carrinho(Decimal valorTotal, List<Aposta<?>> apostas, Long idCarrinho) {
		this.apostas = Collections.unmodifiableList(apostas);
		this.valorTotal = valorTotal;
		this.idCarrinho = idCarrinho;
	}

	public Carrinho() {
		this(Decimal.ZERO, new ArrayList<Aposta<?>>());
	}

	public List<Aposta<?>> getApostas() {
		return apostas;
	}

	// TODO QUARKUS
	public Long getIdCarrinho() {
		return idCarrinho;
	}

	public boolean contemApostas() {
		return !apostas.isEmpty();
	}

	public int getQuantidadeApostas() {
		return apostas.size();
	}

	public void clear() {
		apostas = new ArrayList<Aposta<?>>();
		valorTotal = Decimal.ZERO;
	}

	public Decimal getValorTotal() {
		return valorTotal;
	}

	public boolean contemApostaIdentica(AbstractApostaVO<?> apostaInserida) {
		if (apostaInserida.isSurpresinha()) {
			return false;
		}
		for (Aposta<?> aposta : apostas) {
			if (!aposta.getModalidade().equals(apostaInserida.getModalidade())) {
				continue;
			}
			// verifica se são iguais: modalidade, tipoConcurso, prognósticos, e número do concurso
			boolean condicao1 = aposta.getTipoConcurso().equals(apostaInserida.getTipoConcurso());
			boolean condicao2 = condicao1 && aposta.getPalpites().equals(apostaInserida.getPalpites());
			boolean condicaoFinal = condicao2 && aposta.getConcursoAlvo().equals(apostaInserida.getConcursoAlvo());
			if (condicaoFinal) {
				return true;
			}
		}
		return false;

	}

}
