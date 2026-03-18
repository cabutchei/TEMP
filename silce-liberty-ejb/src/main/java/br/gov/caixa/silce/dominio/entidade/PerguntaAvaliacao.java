package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.autoavaliacao.IndicadorAtivo;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;

/**
 * @author f656372
 */

@Entity
@Table(name = "LCETB057_PERGUNTA_AVALIACAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({

		@NamedQuery(name = PerguntaAvaliacao.NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC, query = "Select pergunta From PerguntaAvaliacao pergunta"
			+ " where pergunta.ativo = ?1 order by pergunta.ordemPergunta asc "),
})

public class PerguntaAvaliacao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC = "PerguntaAvaliacao.NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_PERGUNTA")
	@SequenceGenerator(name = "NU_PERGUNTA", sequenceName = "LCE.LCESQ057_NU_PERGUNTA", allocationSize = 1)
	@Column(name = "NU_PERGUNTA")
	private Long id;

	@Column(name = "DE_PERGUNTA")
	private String descricaoPergunta;

	@Column(name = "NU_ORDEM_PERGUNTA")
	private int ordemPergunta;

	@Column(name = "IC_ATIVO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private IndicadorAtivo ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoPergunta() {
		return descricaoPergunta;
	}

	public void setDescricaoPergunta(String descricaoPergunta) {
		this.descricaoPergunta = descricaoPergunta;
	}

	public int getOrdemPergunta() {
		return ordemPergunta;
	}

	public void setOrdemPergunta(int ordemPergunta) {
		this.ordemPergunta = ordemPergunta;
	}

	public Boolean getAtivo() {
		return ativo.getAtivo();
	}

	public void setAtivo(IndicadorAtivo ativo) {
		this.ativo = ativo;
	}


}
