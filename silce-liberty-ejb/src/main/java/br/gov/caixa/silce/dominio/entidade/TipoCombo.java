package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB052_TIPO_COMBO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({ @NamedQuery(name = TipoCombo.NQ_SELECT_COMBOS_ORDER_BY_ORDEM, query = "select tp From TipoCombo tp order by tp.ordem asc")
})
public class TipoCombo extends AbstractEntidadeEnum<Long, TipoCombo.Tipo> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_COMBOS_ORDER_BY_ORDEM = "TipoCombo.NQ_SELECT_COMBOS_ORDER_BY_ORDEM";

	public enum Tipo implements CaixaEnum<Long> {
		ESPECIAL(1L),
		SUPER_MILIONARIO(2L),
		MILIONARIO(3L),
		MUITO_DINHEIRO(4L),
		SORTE_FACIL(5L), 
		CHANCE_TODO_DIA(6L),
		SORTE_ACUMULADA(7L),
		SORTE_HOJE(8L),
		FEZINHA_DO_MES(9L),
		VIP(10L),
		CAIXA_PRA_ELAS(11L);

		private final Long value;

		private Tipo(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public static Tipo getByTipoCombo(TipoCombo tipoCombo) {
			if (tipoCombo == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), tipoCombo.getId());
		}
		
		public static Tipo getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}
	
	} 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_TIPO_COMBO")
	private Long id;

	@Column(name = "NO_TIPO_COMBO")
	private String nome;

	@Column(name = "DE_TIPO_COMBO")
	private String descricao;

	@Column(name = "NU_ORDEM_TIPO_COMBO")
	private Integer ordem;

	@Column(name = "DT_CRIACAO_TIPO_COMBO")
	@Temporal(TemporalType.DATE)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataCriacao;

	@Column(name = "IC_TIPO_COMBO_DISPONIVEL")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean comboDisponivel = Boolean.TRUE;

	@Transient
	private Boolean novo;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Data dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Boolean getNovo() {
		return novo;
	}

	public void setNovo(Boolean novo) {
		this.novo = novo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected Tipo[] createValores() {
		return Tipo.values();
	}

	@Deprecated
	// Usar o isDisponivel
	public Boolean getComboDisponivel() {
		return comboDisponivel;
	}

	public void setComboDisponivel(Boolean comboDisponivel) {
		this.comboDisponivel = comboDisponivel;
	}

	public boolean isDisponivel() {
		return this.getComboDisponivel() == null ? Boolean.TRUE : this.getComboDisponivel();
	}

}
