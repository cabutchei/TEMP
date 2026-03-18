package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.EtapaFechamento.Etapa;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB051_ETAPA_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class EtapaFechamento extends AbstractEntidadeEnum<Long, Etapa> {

	private static final long serialVersionUID = 1L;

	public enum Etapa implements CaixaEnum<Long> {
		INICIALIZADO(1L),
		GERADO_COMPRAS_EFETIVADAS(2L),
		GERADO_COMPRAS_ESTORNADAS(3L),
		GERADO_APOSTAS_EFETIVADAS(4L),
		GERADO_APOSTAS_DEVOLVIDAS(5L),
		GERADO_PREMIOS_MODALIDADE_CONCURSO(6L),
		GERADO_PREMIOS_MODALIDADE(7L),
		FINALIZADO(8L), 
		GERANDO_COTAS_CONFIRMADAS(9L),
		GERACAO_COTAS_CONFIRMADAS_CONCLUIDA(10L);
		
		private final Long value;

		private Etapa(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
		
		public static Etapa getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		public static Object getByEtapaFechamento(EtapaFechamento etapa) {
			if (etapa == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), etapa.getId());
		}
	}

	@Id
	@Column(name = "NU_ETAPA_FECHAMENTO", insertable = false, updatable = false)
	private Long id;

	@Column(name = "DE_ETAPA_FECHAMENTO", insertable = false, updatable = false)
	private String descricao;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	protected Etapa[] createValores() {
		return Etapa.values();
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("Etapa fechamento [id=");
		builder.append(id);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(']');
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EtapaFechamento other = (EtapaFechamento) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
