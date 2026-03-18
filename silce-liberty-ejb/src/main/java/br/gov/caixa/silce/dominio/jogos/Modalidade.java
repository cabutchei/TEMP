package br.gov.caixa.silce.dominio.jogos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.gov.caixa.silce.dominio.broker.OperacaoSispl;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.StringUtil;

/**
 * Enum de Representação entre Modalidade e seu código para Solicitação.
 */
public enum Modalidade implements CaixaEnum<Integer> {

	// ordenadas em ordem alfabética
	DIA_DE_SORTE(11, "Dia de Sorte", "", OperacaoSispl.REGISTRA_APOSTA_DIA_DE_SORTE, 17),
	DUPLA_SENA(18, "Dupla Sena", "Dupla de Páscoa", OperacaoSispl.REGISTRA_APOSTA_DUPLA_SENA, 11),
	LOTECA(19, "Loteca", "", OperacaoSispl.REGISTRA_APOSTA_LOTECA, 21),
	LOTOFACIL(8, "Lotofácil", "Lotofácil da Independência", OperacaoSispl.REGISTRA_APOSTA_LOTOFACIL, 5),
	LOTOGOL(14, "Lotogol", "", OperacaoSispl.REGISTRA_APOSTA_LOTOGOL, 23),
	LOTOMANIA(16, "Lotomania", "", OperacaoSispl.REGISTRA_APOSTA_LOTOMANIA, 13),
	MAIS_MILIONARIA(9, "Mais Milionária", "", OperacaoSispl.REGISTRA_APOSTA_MAIS_MILIONARIA, 9),
	MEGA_SENA(2, "Mega-Sena", "Mega da Virada", OperacaoSispl.REGISTRA_APOSTA_MEGA_SENA, 3),
	QUINA(3, "Quina", "Quina de São João", OperacaoSispl.REGISTRA_APOSTA_QUINA, 7),
	SUPER_7(7, "Super Sete", "", OperacaoSispl.REGISTRA_APOSTA_SUPER_7, 19),
	TIMEMANIA(20, "Timemania", "", OperacaoSispl.REGISTRA_APOSTA_TIMEMANIA, 15),
	INSTANTANEA(1502, "Instantânea", "", null, 1);

	private final Integer codigo;
	private final String descricao;
	private final OperacaoSispl movimentoSispl;
	private final String descricaoConcursoEspecial;
	private final Integer posicao;

	private Modalidade(Integer codigo, String descricao, String descricaoConcursoEspecial, OperacaoSispl movimentoSispl, Integer posicao) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoConcursoEspecial = descricaoConcursoEspecial;
		this.movimentoSispl = movimentoSispl;
		this.posicao = posicao;
	}

	public static List<Modalidade> getModalidades() {
		return Arrays.asList(Modalidade.values());
	}
	
	public static List<Modalidade> getModalidadesNaoEsportivas() {
		List<Modalidade> modalidades = new ArrayList<Modalidade>();
		for (Modalidade modalidade : Modalidade.values()) {
			if (!modalidade.isEsportivo()) {
				modalidades.add(modalidade);
			}
		}
		return modalidades;
	}

	public static Set<String> getModalidadesNormaisEspeciais() {

		Set<String> modalidades = new HashSet<String>();

		for (Modalidade modalidade : Modalidade.getModalidades()) {
			modalidades.add(modalidade.getCodigo() + "-" + TipoConcurso.NORMAL.getCodigo());

			if (!modalidade.getDescricaoConcursoEspecial().equals("")) {
				modalidades.add(modalidade.getCodigo() + "-" + TipoConcurso.ESPECIAL.getCodigo());
			}
		}

		return modalidades;
	}

	public static Modalidade getByDescricao(String descr) {
		String desc = StringUtil.removeAcentosNotWordsUnderscoreToUpperTrim(descr);
		for (Modalidade modalidade : Modalidade.values()) {
			String normal = StringUtil.removeAcentosNotWordsUnderscoreToUpperTrim(modalidade.descricao);
			String especial = StringUtil.removeAcentosNotWordsUnderscoreToUpperTrim(modalidade.descricaoConcursoEspecial);
			if (normal.equals(desc) || especial.equals(desc)) {
				return modalidade;
			}
		}
		return null;
	}

	public static Modalidade getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}	
	
	public static Modalidade getByEnumName(String enumName) {
		return EnumUtil.valueOf(Modalidade.class, enumName);
	}

	public static Integer getQuantidadesEspeciais() {
		int qtdModalidadesEspeciais = 0;
		for (Modalidade modalidade : Modalidade.values()) {
			if (modalidade.temEspecial()) {
				qtdModalidadesEspeciais++;
			}
		}
		return qtdModalidadesEspeciais;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Boolean temEspecial() {
		return !StringUtil.isEmpty(descricaoConcursoEspecial);
	}

	public String getDescricao(TipoConcurso tipoConcurso) {
		return getDescricao(tipoConcurso == null ? false : tipoConcurso.isEspecial());
	}

	public String getDescricao(Boolean especial) {
		if (especial != null && especial) {
			return getDescricaoConcursoEspecial();
		}
		return getDescricao();
	}

	public boolean isEsportivo() {
		return this.equals(LOTOGOL) || this.equals(LOTECA);
	}
	
	public boolean isNumerico() {
		return !isEsportivo() && !isSuperSete() && !isMaisMilionaria();
	}
	
	public boolean isMegaSena() {
		return this.equals(MEGA_SENA);
	}
	
	public boolean isQuina() {
		return this.equals(QUINA);
	}
	
	public boolean isLotofacil() {
		return this.equals(LOTOFACIL);
	}
	
	public boolean isLotogol() {
		return this.equals(LOTOGOL);
	}
	
	public boolean isLotomania() {
		return this.equals(LOTOMANIA);
	}
	
	public boolean isDuplaSena() {
		return this.equals(DUPLA_SENA);
	}
	
	public boolean isLoteca() {
		return this.equals(LOTECA);
	}
	
	public boolean isTimemania() {
		return this.equals(TIMEMANIA);
	}

	public boolean isDiaDeSorte() {
		return this.equals(DIA_DE_SORTE);
	}

	public boolean isSuperSete() {
		return this.equals(SUPER_7);
	}

	public boolean isMaisMilionaria() {
		return this.equals(MAIS_MILIONARIA);
	}

	public boolean isSISPL2() {
		// Acrescentar modalidades quando o SISPL2 comecar a trata-las
		return Arrays.asList(SUPER_7, MAIS_MILIONARIA).contains(this);
	}

	public static boolean isSISPL2(Modalidade modalidade) {
		// Acrescentar modalidades quando o SISPL2 comecar a trata-las
		return Arrays.asList(SUPER_7, MAIS_MILIONARIA).contains(modalidade);
	}

	@Override
	public Integer getValue() {
		return getCodigo();
	}

	public OperacaoSispl getMovimentoSispl() {
		return movimentoSispl;
	}

	public String getDescricaoConcursoEspecial() {
		return descricaoConcursoEspecial;
	}

	public Integer getPosicao() {
		return posicao;
	}

}
