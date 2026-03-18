package br.gov.caixa.silce.dominio.entidade;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB020_TIPO_OPERACAO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class Operacao extends AbstractEntidadeEnum<Long, OperacaoEnum> {

	private static final long serialVersionUID = 989041624807244370L;
	
	public enum OperacaoEnum implements CaixaEnum<Long> {

		ESTORNO_COMPRA(1L),
		DEVOLUCAO_APOSTA(2L),
		PAGAMENTO_COMPRA(3L),
		PAGAMENTO_PREMIO(4L),
		PAGAMENTO_APOSTA(5L),
		CONSULTA_PAGAMENTO_COMPRA(6L),
		CONSULTA_PAGAMENTO_PREMIO(7L),
		CADASTRO_USUARIO(8L), 
		CRIA_PREFERENCIA_PAGAMENTO(9L),
		CONSULTA_CARTOES(10L),
		EXCLUI_CARTAO(11L),
		CONSULTA_QRCODE_PIX(12L),
		GERACAO_COBRANCA_PIX(13L),
		CONSULTA_COBRANCA_PIX(14L),
		CONSULTA_DEVOLUCAO_PIX(15L),
		PAGAMENTO_COTA_CONTABILIZADA(16L),
		PAGAMENTO_COTA_ENVIADA_SIGEL(17L);

		private OperacaoEnum(Long value) {
			this.value = value;
		}

		private final Long value;

		public Long getValue() {
			return value;
		}

		public static OperacaoEnum getOperacao(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		public static List<OperacaoEnum> getTransacoesFinanceiras() {
			return Arrays.asList(ESTORNO_COMPRA, DEVOLUCAO_APOSTA, PAGAMENTO_COMPRA, PAGAMENTO_PREMIO);
		}

	}
	
	@Id
	@Column(name="NU_TIPO_OPERACAO")
	private Long id;
	
	@Column(name="DE_TIPO_OPERACAO")
	private String nome;
	
	@Override
	public Long getId() {
		return id;
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

	@Override
	protected OperacaoEnum[] createValores() {
		return OperacaoEnum.values();
	}

	@Override
	public String toString() {
		return "Operacao [id=" + id + ", nome=" + nome + "]";
	}

}
