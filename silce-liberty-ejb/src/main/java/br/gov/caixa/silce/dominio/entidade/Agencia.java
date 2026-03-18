package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@NamedQueries({
	@NamedQuery(name = Agencia.NQ_SELECT_BY_NUMERO_NATURAL, query = "Select agencia From Agencia agencia where agencia.id.numeroNatural = ?1")
})
@Table(name = "LCEVW005_UNIDADE", schema = DatabaseConfig.SCHEMA)
public class Agencia extends AbstractEntidade<AgenciaPK> {

	private static final long serialVersionUID = 989041624807244370L;
	
	public static final String NQ_SELECT_BY_NUMERO_NATURAL = "Agencia.findByNumeroNatural";
	
	@EmbeddedId
	private AgenciaPK id;
	
	@Column(name="NO_UNIDADE")
	private String nome;
	
	@Column(name="NU_TP_UNIDADE_U21")
	private Long tipo;
	
	@Column(name="SG_UF_L22")
	private String siglaUF;

	@Override
	public AgenciaPK getId() {
		return id;
	}

	@Override
	public void setId(AgenciaPK id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getSiglaUF() {
		return siglaUF;
	}

	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}

}
