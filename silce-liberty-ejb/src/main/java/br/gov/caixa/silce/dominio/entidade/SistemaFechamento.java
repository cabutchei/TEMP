package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.SistemaFechamento.Sistema;
import br.gov.caixa.util.CaixaEnum;

@Entity
@Table(name = "LCETB027_SISTEMA_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class SistemaFechamento extends AbstractEntidadeEnum<Long, Sistema> {

	private static final long serialVersionUID = 989041624807244370L;
	
	public enum Sistema implements CaixaEnum<Long> {
		SIDEC(1L),
		SINAF(2L),
		NSGD(3L),
		MKP(4L);

		private final Long value;

		private Sistema(Long value) {
			this.value = value;
			
		}
		
		@Override
		public Long getValue() {
			return value;
		}
	}
	
	@Id
	@Column(name="NU_SISTEMA_FECHAMENTO")
	private Long id;

	@Column(name="NO_SISTEMA_FECHAMENTO")
	private String nome;
	
	@Deprecated
	@Column(name = "IC_TIPO_ENVIO")
	//Nao mapeei para um outro tipo ainda, pois não deve ter outro alem de arquivo (deprecated)
	//Mapear pq agora exite um novo tipo de envio (SIAEF)
	private Character tipo;
	
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

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	@Override
	protected Sistema[] createValores() {
		return Sistema.values();
	}
	
}
