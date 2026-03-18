package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * @author c101482
 */
@Entity
@Table(name = "LCETB043_TEXTO_INFORMATIVO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(
			name = TextoInformativo.NQ_SELECT_BY_ASSUNTO,
			query = "Select textoInformativo From TextoInformativo textoInformativo "
				+ " Where lower(textoInformativo.assunto) like ?1")
})
public class TextoInformativo extends AbstractEntidadeEnum<Long, TextoInformativo.Texto> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_ASSUNTO = "TextoInformativo.selectByAssunto";

	public enum Texto implements CaixaEnum<Long> {

		JOGO_RESPONSAVEL(1L),
		CABECALHO_SIMULADOR(2L),
		
        CABECALHO_MEGA_VIRADA(3L),
		DES_BREVE_MEGA_VIRADA(4L),
		DES_COMPLE_MEGA_VIRADA(5L),

        CABECALHO_LOTOFACIL_INDEPENDENCIA(6L),
		DES_BREVE_LOTOFACIL_INDEPENDENCIA(7L),
		DES_COMPLE_LOTOFACIL_INDEPENDENCIA(8L),

        CABECALHO_QUINA_SAO_JOAO(9L),
		DES_BREVE_QUINA_SAO_JOAO(10L),
		DES_COMPLE_QUINA_SAO_JOAO(11L),

        CABECALHO_DUPLA_PASCOA(12L),
		DES_BREVE_DUPLA_PASCOA(13L),
		DES_COMPLE_DUPLA_PASCOA(14L),

        CABECALHO_MEGA_SENA(15L),
		DES_BREVE_MEGA_SENA(16L),
		DES_COMPLE_MEGA_SENA(17L),

        CABECALHO_LOTOFACIL(18L),
		DES_BREVE_LOTOFACIL(19L),
		DES_COMPLE_LOTOFACIL(20L),

        CABECALHO_QUINA(21L),
		DES_BREVE_QUINA(22L),
		DES_COMPLE_QUINA(23L),

        CABECALHO_LOTOMANIA(24L),
		DES_BREVE_LOTOMANIA(25L),
		DES_COMPLE_LOTOMANIA(26L),

        CABECALHO_TIMEMANIA(27L),
		DES_BREVE_TIMEMANIA(28L),
		DES_COMPLE_TIMEMANIA(29L),

        CABECALHO_DUPLA_SENA(30L),
		DES_BREVE_DUPLA_SENA(31L),
		DES_COMPLE_DUPLA_SENA(32L),

        CABECALHO_LOTECA(33L),
		DES_BREVE_LOTECA(34L),
		DES_COMPLE_LOTECA(35L),

        CABECALHO_LOTOGOL(36L),
		DES_BREVE_LOTOGOL(37L),
		DES_COMPLE_LOTOGOL(38L),

        CABECALHO_DIA_SORTE(39L),
		DES_BREVE_DIA_SORTE(40L),
		DES_COMPLE_DIA_SORTE(41L),

        CABECALHO_PG_INICIAL(42L),

		DES_BREVE_CARRINHO_COMPRAS(43L),
		DES_COMPLE_CARRINHO_COMPRAS(44L),
		
		CABECALHO_SUPER_7(45L),
		DES_BREVE_SUPER_7(46L),
		DES_COMPLE_SUPER_7(47L),

		DES_SSO_INDISPONIVEL(48L),

		CABECALHO_MAIS_MILIONARIA(49L), 
		DES_BREVE_MAIS_MILIONARIA(50L),
		DES_COMPLE_MAIS_MILIONARIA(51L),
		DES_BREVE_MILIONARIA_TREVO(52L),
		CHAMADA_PRINCIPAL_MAIS_MILIONARIA(53L), 
		CHAMADA_PRINCIPAL_MEGA_SENA(54L),
		CHAMADA_PRINCIPAL_LOTO_FACIL(55L),
		CHAMADA_PRINCIPAL_QUINA(56L), 
		CHAMADA_PRINCIPAL_LOTOMANIA(57L), 
		CHAMADA_PRINCIPAL_TIMEMANIA(58L), 
		CHAMADA_PRINCIPAL_DUPLA_SENA(59L), 
		CHAMADA_PRINCIPAL_LOTECA(60L), 
		CHAMADA_PRINCIPAL_DIA_SORTE(61L), 
		CHAMADA_PRINCIPAL_SUPER_SETE(62L),
		CHAMADA_PRINCIPAL_LOTOGOL(63L),
		CHAMADA_PRINCIPAL_DUPLA_ESPECIAL(64L),
		CHAMADA_PRINCIPAL_QUINA_ESPECIAL(65L),
		CHAMADA_PRINCIPAL_LOTO_FACIL_ESPECIAL(66L),
		CHAMADA_PRINCIPAL_MEGA_SENA_ESPECIAL(67L),
		CABECALHO_INSTANTANEA(68L),
		CHAMADA_PRINCIPAL_INSTANTANEA(69L),

		MENSAGEM_BAIXAR_APP_01(70L), MENSAGEM_BAIXAR_APP_02(71L), URL_BAIXAR_APP_ANDROID(72L), URL_BAIXAR_APP_IOS(73L);

		private final Long value;

		private Texto(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public static Texto getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}
	}

	@Id
	@Column(name = "NU_TEXTO_INFORMATIVO")
	private Long id;

	@Column(name = "DE_CONTEUDO_TEXTO")
	private String conteudo;

	@Column(name = "DE_ASSUNTO_TEXTO")
	private String assunto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected Texto[] createValores() {
		return Texto.values();
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
}
