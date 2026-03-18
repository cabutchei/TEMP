package br.gov.caixa.silce.dominio.jogos;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.util.Validate;

enum SituacaoConcursoSispl2 {
	ABERTO(0, "Aberto"),
	ENCERRADO(1, "Encerrado"),
	HOMOLOGADO(3, "Homologado"),
	PRESCRITO(5, "Prescrito"),
	NAO_INICIALIZADO(9, "Não Inicializado");
	
	private final Integer codigo;
	private final String descricao;

	private SituacaoConcursoSispl2(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoConcursoSispl2 getByCodigo(Integer codigo) {
		for (SituacaoConcursoSispl2 situacaoConcurso : SituacaoConcursoSispl2.values()) {
			if (situacaoConcurso.getCodigo().equals(codigo)) {
				return situacaoConcurso;
			}
		}
		return null;
	}
}

public enum SituacaoConcurso {
		
	ABERTO(0, "Aberto"),
	ENCERRADO(1, "Encerrado"),
	APURADO(2, "Apurado"),
	HOMOLOGADO(3, "Homologado"),
	CONTABILIZADO(4, "Contabilizado"),
	PRESCRITO(5, "Prescrito"),
	EM_APURACAO(6, "Em Apuração"),
	PRESCRITO_CONTABILIZADO(7, "Prescrito - Não Contabilizado"),
	CANCELADO(8, "Cancelado"),
	NAO_INICIALIZADO(9, "Não Inicializado");

	private final Integer codigo;
	private final String descricao;

	private SituacaoConcurso(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoConcurso getByCodigo(Integer codigo) {
		for (SituacaoConcurso situacaoConcurso : SituacaoConcurso.values()) {
			if (situacaoConcurso.getCodigo().equals(codigo)) {
				return situacaoConcurso;
			}
		}
		return null;
	}

	public static SituacaoConcurso converteFromSISPL2(Integer codigo) {
		SituacaoConcursoSispl2 byCodigo = SituacaoConcursoSispl2.getByCodigo(codigo);
		Validate.notNull(byCodigo, "Situação do Concurso SISPL2");
		switch (byCodigo) {
			case NAO_INICIALIZADO:
				return SituacaoConcurso.NAO_INICIALIZADO;
			case ABERTO:
				return SituacaoConcurso.ABERTO;
			case HOMOLOGADO:
				return SituacaoConcurso.HOMOLOGADO;
			case PRESCRITO:
				return SituacaoConcurso.PRESCRITO;
			case ENCERRADO:
				return SituacaoConcurso.ENCERRADO;
			default:
				return null;
		}
	}

	public static List<SituacaoConcurso> getSituacoesHomologado() {
		return Arrays.asList(HOMOLOGADO, CONTABILIZADO, PRESCRITO, PRESCRITO_CONTABILIZADO);
	}
		
}
