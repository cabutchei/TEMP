package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB047_MODELO_NOTIFICACAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(
		name = ModeloNotificacao.NQ_SELECT_BY_ID,
		query = "SELECT modelo FROM ModeloNotificacao modelo WHERE modelo.id = ?1"
	)
})
@NamedNativeQueries({

})
public class ModeloNotificacao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_ID = "ModeloNotificacao.NQ_SELECT_BY_ID";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_MODELO_NOTIFICACAO")
	private Long id;

	@Column(name = "DE_TITULO")
	private String titulo;

	@Column(name = "DE_CONTEUDO")
	private String conteudo;

	@Column(name = "DE_LINK")
	private String link;

	@Column(name = "IM_ICONE")
	@Lob
	private byte[] imagem;

	@Column(name = "DE_PERFIL")
	private String perfil;

	@Column(name = "TS_CRIACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataCriacao;

	@Column(name = "TS_ALTERACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataAlteracao;

	@Column(name = "IC_SUBCANAL_NOTIFICACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanal;

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

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Data dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Data getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Data dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

}
