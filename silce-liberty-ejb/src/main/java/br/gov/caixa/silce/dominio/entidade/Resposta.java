package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB035_RESPOSTA_PERGUNTA", schema = DatabaseConfig.SCHEMA)
public class Resposta extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_RESPOSTA_PERGUNTA")
	private Long id;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_PERGUNTA_ASSUNTO")
	private Pergunta pergunta;

	@Column(name = "DE_RESPOSTA_PERGUNTA")
	private String conteudo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

}
