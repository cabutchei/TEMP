package br.gov.caixa.silce.dominio.entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB033_ASSUNTO_DUVIDA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Secao.NQ_SELECT_BY_POSICAO, query = "Select secao from Secao secao where secao.posicao = ?1"),
		@NamedQuery(name = Secao.NQ_SELECT_MAX_POSICAO, query = "Select max(secao.posicao) from Secao secao"),
		@NamedQuery(name = Secao.NQ_SELECT_ALL_ORDERED, query = "Select secao from Secao secao order by secao.posicao"),
		@NamedQuery(name = Secao.NQ_UPDATE_ALL_MAIOR_QUE_POSICAO, query = "UPDATE Secao s SET s.posicao = s.posicao - 1 WHERE s.posicao > ?1"),
})
public class Secao extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_POSICAO = "Secao.findByPosicao";
	public static final String NQ_SELECT_MAX_POSICAO = "Secao.findMaxPosicao";
	public static final String NQ_SELECT_ALL_ORDERED = "Secao.findAllOrdered";
	public static final String NQ_UPDATE_ALL_MAIOR_QUE_POSICAO = "Secao.updateAllMaiorQuePosicao";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_ASSUNTO_DUVIDA")
	private Long id;

	@Column(name = "DE_ASSUNTO_DUVIDA")
	private String nome;

	@Column(name = "NU_POSICAO_ASSUNTO_DUVIDA")
	private Integer posicao;

	@Transient
	private List<Pergunta> perguntas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

}
