package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

/**
 * 
 * @author c101482
 *
 */
@Entity
@Table(name = "LCETB031_CODIGO_RESGATE", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(
			name = CodigoResgate.NQ_FIND_BY_APOSTA_CANAL, 
			query = "Select cr from CodigoResgate cr JOIN FETCH cr.apostaComprada where cr.apostaComprada.id = ?1 and cr.canal = ?2"),
		@NamedQuery(name = CodigoResgate.NQ_DELETE_BY_APOSTA_CANAL, query = "delete from CodigoResgate cr where cr.apostaComprada.id = ?1 and cr.canal = ?2")
})
public class CodigoResgate extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_FIND_BY_APOSTA_CANAL = "CodigoResgate.NQ_FIND_BY_APOSTA_CANAL";
	public static final String NQ_DELETE_BY_APOSTA_CANAL = "CodigoResgate.NQ_DELETE_BY_APOSTA_CANAL";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_CODIGO_RESGATE")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_APOSTA_COMPRADA", referencedColumnName = "NU_APOSTA_COMPRADA")
	private ApostaComprada apostaComprada;

	@Column(name = "CO_RESGATE")
	private String codigoResgate;
	
	@Column(name = "QT_TENTATIVA_RESGATE")
	private Integer quantidadeTentativaResgate = Integer.valueOf(0);
	
	@Column(name = "TS_EXPIRACAO_CODIGO_RESGATE")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data validadeCodigoResgate;

	@Column(name = "NU_CANAL")
	private Integer canal = Integer.valueOf(0);

	@Transient
	private String codigoResgateAberto;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the apostaComprada
	 */
	public ApostaComprada getApostaComprada() {
		return apostaComprada;
	}

	/**
	 * @param apostaComprada
	 *            the apostaComprada to set
	 */
	public void setApostaComprada(ApostaComprada apostaComprada) {
		this.apostaComprada = apostaComprada;
	}

	/**
	 * @return the codigoResgate
	 */
	public String getCodigoResgate() {
		return codigoResgate;
	}

	/**
	 * @param codigoResgate
	 *            the codigoResgate to set
	 */
	public void setCodigoResgate(String codigoResgate) {
		this.codigoResgate = codigoResgate;
	}

	/**
	 * @return the quantidadeTentativaResgate
	 */
	public Integer getQuantidadeTentativaResgate() {
		return quantidadeTentativaResgate;
	}

	/**
	 * @param quantidadeTentativaResgate
	 *            the quantidadeTentativaResgate to set
	 */
	public void setQuantidadeTentativaResgate(Integer quantidadeTentativaResgate) {
		this.quantidadeTentativaResgate = quantidadeTentativaResgate;
	}

	/**
	 * @return the validadeCodigoResgate
	 */
	public Data getValidadeCodigoResgate() {
		return validadeCodigoResgate;
	}

	/**
	 * @param validadeCodigoResgate
	 *            the validadeCodigoResgate to set
	 */
	public void setValidadeCodigoResgate(Data validadeCodigoResgate) {
		this.validadeCodigoResgate = validadeCodigoResgate;
	}

	/**
	 * @return the canal
	 */
	public Integer getCanal() {
		return canal;
	}

	/**
	 * @param canal
	 *            the canal to set
	 */
	public void setCanal(Integer canal) {
		this.canal = canal;
	}

	/**
	 * @return the codigoResgateAberto
	 */
	public String getCodigoResgateAberto() {
		return codigoResgateAberto;
	}

	/**
	 * @param codigoResgateAberto
	 *            the codigoResgateAberto to set
	 */
	public void setCodigoResgateAberto(String codigoResgateAberto) {
		this.codigoResgateAberto = codigoResgateAberto;
	}
	

	
}
