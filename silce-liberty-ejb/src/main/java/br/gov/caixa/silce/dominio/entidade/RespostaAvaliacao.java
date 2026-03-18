package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.autoavaliacao.IndicadorResposta;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

/**
 * @author f656372
 */

@Entity
@Table(name = "LCETB058_RESPOSTA_AVALIACAO", schema = DatabaseConfig.SCHEMA)
// @NamedQueries({
//
// @NamedQuery(name = RespostaAvaliacao.NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC, query = "Select pergunta From
// PerguntaAvaliacao pergunta"
// + " where pergunta.ativo = ?1 order by pergunta.ordemPergunta asc "),
// })

public class RespostaAvaliacao extends AbstractEntidade<Long> {

	// public static final String NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC =
	// "PerguntaAvaliacao.NQ_SELECT_TODAS_PERGUNTAS_ORDER_ASC";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_RESPOSTA")
	@SequenceGenerator(name = "NU_RESPOSTA", sequenceName = "LCE.LCESQ058_NU_RESPOSTA", allocationSize = 1)
	@Column(name = "NU_RESPOSTA")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_PERGUNTA", referencedColumnName = "NU_PERGUNTA")
	private PerguntaAvaliacao perguntaAvaliacao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
	private Apostador apostador;

	@Column(name = "IC_RESPOSTA")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private IndicadorResposta resposta;

	@Column(name = "DE_RESPOSTA")
	private String descricaoResposta;

	@Column(name = "TS_RESPOSTA")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataHoraResposta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerguntaAvaliacao getPerguntaAvaliacao() {
		return perguntaAvaliacao;
	}

	public void setPerguntaAvaliacao(PerguntaAvaliacao perguntaAvaliacao) {
		this.perguntaAvaliacao = perguntaAvaliacao;
	}

	public Long getApostador() {
		return apostador.getId();
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public IndicadorResposta getResposta() {
		return resposta;
	}

	public void setResposta(Boolean resposta) {
		if (resposta) {
			this.resposta = IndicadorResposta.VERDADEIRO;
		} else {
			this.resposta = IndicadorResposta.FALSO;
		}
	}

	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}

	public Data getDataHoraResposta() {
		return dataHoraResposta;
	}

	public void setDataHoraResposta(Data dataHoraResposta) {
		this.dataHoraResposta = dataHoraResposta;
	}



}
