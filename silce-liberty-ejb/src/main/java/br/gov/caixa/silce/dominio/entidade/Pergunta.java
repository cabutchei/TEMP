package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB034_PERGUNTA_ASSUNTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Pergunta.NQ_SELECT_BY_POSICAO_SECAO, query = "Select pergunta from Pergunta pergunta where pergunta.posicao = ?1 and pergunta.secao.id = ?2"),
		@NamedQuery(name = Pergunta.NQ_SELECT_MAX_POSICAO, query = "Select max(pergunta.posicao) from Pergunta pergunta where pergunta.secao.id = ?1"),
		@NamedQuery(name = Pergunta.NQ_SELECT_ORDERED_BY_SECAO, query = "Select pergunta from Pergunta pergunta where pergunta.secao.id = ?1 order by pergunta.posicao"),
		@NamedQuery(name = Pergunta.NQ_UPDATE_ALL_MAIOR_QUE_POSICAO, query = "UPDATE Pergunta p SET p.posicao = p.posicao - 1 WHERE p.posicao > ?1 and p.secao.id = ?2")
})
public class Pergunta extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_POSICAO_SECAO = "Pergunta.findByPosicao";
	public static final String NQ_SELECT_MAX_POSICAO = "Pergunta.findMaxPosicao";
	public static final String NQ_SELECT_ORDERED_BY_SECAO = "Pergunta.findAllOrdered";
	public static final String NQ_UPDATE_ALL_MAIOR_QUE_POSICAO = "Pergunta.updateAllMaiorQuePosicao";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_PERGUNTA_ASSUNTO")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_ASSUNTO_PERGUNTA")
	private Secao secao;

	@OneToOne(mappedBy = "pergunta", cascade = CascadeType.ALL)
	private Resposta resposta = new Resposta();

	@Column(name = "NU_POSICAO_DUVIDA")
	private Integer posicao;

	@Column(name = "DE_PERGUNTA_ASSUNTO")
	private String conteudo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

}
