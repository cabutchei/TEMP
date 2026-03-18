package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao.Situacao;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * @author c142924
 *
 */
@Entity
@Table(name = "LCETB060_STCO_RVRSA_COTA_BOLAO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class SituacaoReservaCotaBolao extends AbstractEntidadeEnum<Long, Situacao> {

	private static final long serialVersionUID = 1L;

	public enum Situacao implements CaixaEnum<Long> {
		INICIADA(1L),
		RESERVADA(2L),
		CANCELADA(3L),
		EM_PAGAMENTO(4L),
		CONFIRMADA(5L),
		EM_CANCELAMENTO(6L),
		CONTABILIZADA(7L);
		
		private final Long value;
		
		private Situacao(Long value) {
			this.value = value;
		}
		
		public Long getValue() {
			return value;
		}

		public static Situacao getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		/**
		 * @return os dois estados em que a aposta está em processamento
		 */
		public static Situacao[] getSituacoesEmProcessamento() {
			return new Situacao[] { RESERVADA, EM_PAGAMENTO };
		}

		public static Situacao[] getSituacoesPendenteParaFecharRevenda() {
			return new Situacao[] { INICIADA, RESERVADA, EM_PAGAMENTO, EM_CANCELAMENTO };
		}

		public static Object getBySituacaoReservaCotaBolao(SituacaoReservaCotaBolao situacao) {
			if (situacao == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), situacao.getId());
		}
	}

	@Id
	@Column(name = "NU_SITUACAO_RESERVA_COTA_BOLAO", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "NO_RESERVA_SITUACAO_COTA_BOLAO", insertable = false, updatable = false)
	private String nome;

	@Column(name = "DE_SITUACAO_RESERVA_COTA_BOLAO", insertable = false, updatable = false)
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
	protected Situacao[] createValores() {
		return Situacao.values();
	}

	@Override
	public String toString() {
		return "SituacaoReservaCotaBolao [id=" + id + ", nome=" + nome + "descricao=" + descricao + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
