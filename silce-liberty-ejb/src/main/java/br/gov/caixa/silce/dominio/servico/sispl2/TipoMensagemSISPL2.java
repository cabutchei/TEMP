package br.gov.caixa.silce.dominio.servico.sispl2;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.silce.dominio.TipoEstatistica;
import br.gov.caixa.util.CaixaEnum;

public enum TipoMensagemSISPL2 implements CaixaEnum<Integer> {
	MENSAGEM_USUARIO(1, "Mensagens para o Usuário"), MENSAGEM_SISTEMA(2, "Mensagens do Sistema"), ERROS_VALIDACAO(3, "Erro de Validação");
	
	private final Integer codigo;
	private final String descricao;

	private TipoMensagemSISPL2(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static List<TipoEstatistica> getTiposEstatistica() {
		return Arrays.asList(TipoEstatistica.values());
	}
	
	@Override
	public Integer getValue() {
		return codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
