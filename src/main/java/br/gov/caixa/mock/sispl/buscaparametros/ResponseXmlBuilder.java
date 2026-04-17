package br.gov.caixa.mock.sispl.buscaparametros;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;

final class ResponseXmlBuilder {

	private static final DateTimeFormatter INPUT_DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final DateTimeFormatter OUTPUT_HEADER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter OUTPUT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static final String INDENT = "  ";

	private ResponseXmlBuilder() {
	}

	static String build(JsonNode root, MqConfig config) {
		StringBuilder xml = new StringBuilder(16_384);
		OffsetDateTime now = OffsetDateTime.now(config.zoneId().getRules().getOffset(Instant.now()));

		xml.append(XML_HEADER).append('\n');
		xml.append("<bpj:SERVICO_SAIDA")
				.append(" xmlns:sibar=\"http://caixa.gov.br/sibar\"")
				.append(" xmlns:sispl_base=\"http://caixa.gov.br/sispl/base\"")
				.append(" xmlns:bpj=\"http://caixa.gov.br/sispl/busca_parametros_jogos\"")
				.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")
				.append('\n');

		appendLine(xml, 1, "<sibar:HEADER>");
		appendLine(xml, 2, "<VERSAO>1</VERSAO>");
		appendLine(xml, 2, "<USUARIO_SERVICO>" + XmlUtil.escape(config.headerUserService()) + "</USUARIO_SERVICO>");
		appendLine(xml, 2, "<USUARIO>" + XmlUtil.escape(config.headerUser()) + "</USUARIO>");
		appendLine(xml, 2, "<OPERACAO>" + XmlUtil.escape(config.headerOperation()) + "</OPERACAO>");
		appendLine(xml, 2, "<SISTEMA_ORIGEM>SISPL</SISTEMA_ORIGEM>");
		appendLine(xml, 2, "<DATA_HORA>" + OUTPUT_HEADER.format(now) + "</DATA_HORA>");
		appendLine(xml, 1, "</sibar:HEADER>");
		appendLine(xml, 1, "<COD_RETORNO>" + config.codRetorno() + "</COD_RETORNO>");
		appendLine(xml, 1, "<ORIGEM_RETORNO>" + XmlUtil.escape(config.origemRetorno()) + "</ORIGEM_RETORNO>");
		appendLine(xml, 1, "<MSG_RETORNO>" + XmlUtil.escape(config.msgRetorno()) + "</MSG_RETORNO>");
		appendLine(xml, 1, "<DADOS>");

		JsonNode parametros = root.path("payload").path("parametros");
		if (parametros.isArray()) {
			for (JsonNode parametro : parametros) {
				appendConcursoCanal(xml, parametro.path("parametroJogo"), config.zoneId());
				if (config.includeProximoConcurso() && !parametro.path("proximoConcurso").isMissingNode() && !parametro.path("proximoConcurso").isNull()) {
					appendConcursoCanal(xml, parametro.path("proximoConcurso"), config.zoneId());
				}
			}
		}

		appendLine(xml, 1, "</DADOS>");
		xml.append("</bpj:SERVICO_SAIDA>").append('\n');
		return xml.toString();
	}

	private static void appendConcursoCanal(StringBuilder xml, JsonNode node, ZoneId zoneId) {
		if (node.isMissingNode() || node.isNull()) {
			return;
		}

		JsonNode concurso = node.path("concurso");
		if (concurso.isMissingNode() || concurso.isNull()) {
			return;
		}

		JsonNode modalidadeDetalhada = concurso.path("modalidadeDetalhada");
		int modalidadeCodigo = modalidadeDetalhada.path("id").asInt(-1);
		if (modalidadeCodigo <= 0 || modalidadeCodigo == 1502) {
			return;
		}

		String modalidade = concurso.path("modalidade").asText("");
		String channelStatus = mapSituacaoConcursoCanal(node.path("situacaoConcursoCanal").asText(""));
		String concursoType = mapConcursoType(modalidade);

		appendLine(xml, 2, "<CONCURSO_CANAL SITUACAO=\"" + channelStatus + "\">");

		StringBuilder attrs = new StringBuilder();
		appendAttr(attrs, "xsi:type", concursoType);
		appendAttr(attrs, "NUMERO", valueAsString(concurso.path("numero")));
		appendAttr(attrs, "MODALIDADE", Integer.toString(modalidadeCodigo));
		appendAttr(attrs, "TIPO", mapTipoConcurso(concurso.path("tipoConcurso").asText("NORMAL")));
		appendAttr(attrs, "SITUACAO", mapSituacaoConcurso(concurso.path("situacao").asText("")));
		appendAttr(attrs, "DATA_ABERTURA", formatDateTime(concurso.path("dataAbertura"), zoneId));
		appendAttr(attrs, "DATA_FECHAMENTO", formatDateTime(concurso.path("dataFechamento"), zoneId));
		appendAttr(attrs, "VALOR_APOSTA_UNITARIA", decimalText(concurso.path("valorApostaMinima")));
		appendAttr(attrs, "VALOR_ESTIMADO_PREMIO", decimalText(concurso.path("estimativa")));

		switch (modalidade) {
			case "LOTECA" -> {
				appendAttr(attrs, "DATA_PARTIDA_FINAL", formatDateTime(concurso.path("dataHoraSorteio"), zoneId));
				appendAttr(attrs, "VALOR_APOSTA_MINIMA", decimalText(node.path("valorApostaMinima")));
			}
			case "LOTOGOL" -> appendAttr(attrs, "DATA_PARTIDA_FINAL", formatDateTime(concurso.path("dataHoraSorteio"), zoneId));
			default -> {
				appendAttr(attrs, "DATA_SORTEIO", formatDateTime(concurso.path("dataHoraSorteio"), zoneId));
				appendAttr(attrs, "MAIOR_DEZENA", valueAsString(node.path("prognosticoMaximo")));
				appendAttr(attrs, "QUANTIDADE_MINIMA_PALPITES", valueAsString(node.path("quantidadeMinima")));
				appendAttr(attrs, "QUANTIDADE_MAXIMA_PALPITES", valueAsString(node.path("quantidadeMaxima")));
				appendAttr(attrs, "QUANTIDADE_MAXIMA_SURPRESINHAS", valueAsString(node.path("quantidadeSurpresinhas")));
				appendAttr(attrs, "ACEITA_ESPELHO", valueAsString(node.path("aceitaEspelho")));
				if ("MAIS_MILIONARIA".equals(modalidade)) {
					appendAttr(attrs, "PROGNOSTICO_MAXIMO_TREVO", valueAsString(node.path("trevos").path("prognosticoMaximoTrevo")));
					appendAttr(attrs, "QUANTIDADE_MINIMA_TREVO", valueAsString(node.path("trevos").path("quantidadeMinimaTrevo")));
					appendAttr(attrs, "QUANTIDADE_MAXIMA_TREVO", valueAsString(node.path("trevos").path("quantidadeMaximaTrevo")));
				}
			}
		}

		appendLine(xml, 3, "<CONCURSO" + attrs + ">");
		appendBody(xml, node, modalidade);
		appendLine(xml, 3, "</CONCURSO>");
		appendLine(xml, 3, "<CANAL CODIGO_CANAL=\"9880\" NOME=\"SILCE\"/>");
		appendLine(xml, 2, "</CONCURSO_CANAL>");
	}

	private static void appendBody(StringBuilder xml, JsonNode node, String modalidade) {
		appendTeimosinhas(xml, node.path("teimosinhas"));

		switch (modalidade) {
			case "TIMEMANIA" -> {
				appendValoresAposta(xml, node.path("valoresAposta"), modalidade);
				appendEquipes(xml, node.path("equipes"));
			}
			case "DIA_DE_SORTE" -> {
				appendValoresAposta(xml, node.path("valoresAposta"), modalidade);
				appendMeses(xml, node.path("meses"));
			}
			case "LOTECA", "LOTOGOL" -> {
				appendPartidas(xml, node.path("partidas"));
				appendValoresAposta(xml, node.path("valoresAposta"), modalidade);
			}
			default -> appendValoresAposta(xml, node.path("valoresAposta"), modalidade);
		}
	}

	private static void appendTeimosinhas(StringBuilder xml, JsonNode teimosinhas) {
		if (!teimosinhas.isArray()) {
			return;
		}
		for (JsonNode teimosinha : teimosinhas) {
			if (teimosinha.isInt() && teimosinha.asInt() > 0) {
				appendLine(xml, 4, "<QUANTIDADE_TEIMOSINHAS>" + teimosinha.asInt() + "</QUANTIDADE_TEIMOSINHAS>");
			}
		}
	}

	private static void appendValoresAposta(StringBuilder xml, JsonNode valores, String modalidade) {
		if (!valores.isArray()) {
			return;
		}
		for (JsonNode valor : valores) {
			StringBuilder attrs = new StringBuilder();
			switch (modalidade) {
				case "LOTECA" -> {
					appendAttr(attrs, "QUANTIDADE_DUPLOS", valueAsString(valor.path("quantidadeDuplos")));
					appendAttr(attrs, "QUANTIDADE_TRIPLOS", valueAsString(valor.path("quantidadeTriplos")));
				}
				case "LOTOGOL" -> appendAttr(attrs, "NU_PALPITES", valueAsString(valor.path("numeroApostas")));
				default -> appendAttr(attrs, "NU_PALPITES", valueAsString(valor.path("numeroPrognosticos")));
			}
			appendAttr(attrs, "VALOR", decimalText(valor.path("valor")));
			appendLine(xml, 4, "<VALOR_APOSTA" + attrs + "/>");
		}
	}

	private static void appendEquipes(StringBuilder xml, JsonNode equipes) {
		if (!equipes.isArray()) {
			return;
		}
		for (JsonNode equipe : equipes) {
			StringBuilder attrs = new StringBuilder();
			appendAttr(attrs, "NUMERO", valueAsString(equipe.path("numero")));
			appendAttr(attrs, "NOME", equipe.path("nome").asText(""));
			appendAttr(attrs, "NOME_REDUZIDO", equipe.path("descricaoCurta").asText(""));
			appendAttr(attrs, "UF", blankToNull(equipe.path("uf").asText("")));
			appendAttr(attrs, "NU_PAIS", valueAsString(equipe.path("numeroPais")));
			appendAttr(attrs, "NOME_PAIS", equipe.path("pais").asText(""));
			appendAttr(attrs, "SIGLA_PAIS", equipe.path("siglaPais").asText(""));
			appendAttr(attrs, "INDICADOR_SELECAO", valueAsString(equipe.path("indicadorSelecao")));
			appendLine(xml, 4, "<EQUIPE_ESPORTIVA" + attrs + "/>");
		}
	}

	private static void appendMeses(StringBuilder xml, JsonNode meses) {
		if (!meses.isArray()) {
			return;
		}
		for (JsonNode mes : meses) {
			appendLine(xml, 4, "<NU_MES>" + mes.path("numero").asInt() + "</NU_MES>");
		}
	}

	private static void appendPartidas(StringBuilder xml, JsonNode partidas) {
		if (!partidas.isArray()) {
			return;
		}
		for (JsonNode partida : partidas) {
			appendLine(xml, 4, "<PARTIDA NU_PARTIDA=\"" + partida.path("numero").asInt() + "\">");
			appendEquipe(xml, 5, "EQUIPE_2", partida.path("equipe2"));
			appendEquipe(xml, 5, "EQUIPE_1", partida.path("equipe1"));
			String letra = blankToNull(partida.path("letraLegenda").asText(""));
			String descricao = blankToNull(partida.path("descricaoLegenda").asText(""));
			if (letra != null) {
				appendLine(xml, 5, "<LEG_CURTA_CAMPEONATO>" + XmlUtil.escape(letra) + "</LEG_CURTA_CAMPEONATO>");
			}
			if (descricao != null) {
				appendLine(xml, 5, "<LEG_LONGA_CAMPEONATO>" + XmlUtil.escape(descricao) + "</LEG_LONGA_CAMPEONATO>");
			}
			appendLine(xml, 4, "</PARTIDA>");
		}
	}

	private static void appendEquipe(StringBuilder xml, int indentLevel, String elementName, JsonNode equipe) {
		if (equipe.isMissingNode() || equipe.isNull()) {
			return;
		}
		StringBuilder attrs = new StringBuilder();
		appendAttr(attrs, "NUMERO", valueAsString(equipe.path("numero")));
		appendAttr(attrs, "NOME", equipe.path("nome").asText(""));
		appendAttr(attrs, "NOME_REDUZIDO", equipe.path("descricaoCurta").asText(""));
		appendAttr(attrs, "UF", blankToNull(equipe.path("uf").asText("")));
		appendAttr(attrs, "NU_PAIS", valueAsString(equipe.path("numeroPais")));
		appendAttr(attrs, "NOME_PAIS", equipe.path("pais").asText(""));
		appendAttr(attrs, "SIGLA_PAIS", equipe.path("siglaPais").asText(""));
		appendAttr(attrs, "INDICADOR_SELECAO", valueAsString(equipe.path("indicadorSelecao")));
		appendLine(xml, indentLevel, "<" + elementName + attrs + "/>");
	}

	private static String mapConcursoType(String modalidade) {
		return switch (modalidade) {
			case "LOTECA" -> "sispl_base:CONCURSO_LOTECA_TYPE";
			case "LOTOGOL" -> "sispl_base:CONCURSO_LOTOGOL_TYPE";
			case "TIMEMANIA" -> "sispl_base:CONCURSO_TIMEMANIA_TYPE";
			case "DIA_DE_SORTE" -> "sispl_base:CONCURSO_DIA_DE_SORTE_TYPE";
			case "MAIS_MILIONARIA" -> "sispl_base:CONCURSO_MAIS_MILIONARIA_TYPE";
			default -> "sispl_base:CONCURSO_NUMERICO_TYPE";
		};
	}

	private static String mapTipoConcurso(String tipo) {
		return switch (tipo.toUpperCase(Locale.ROOT)) {
			case "ESPECIAL" -> "2";
			default -> "1";
		};
	}

	private static String mapSituacaoConcurso(String situacao) {
		return switch (situacao.toUpperCase(Locale.ROOT)) {
			case "ABERTO" -> "0";
			case "ENCERRADO" -> "1";
			case "APURADO" -> "2";
			case "HOMOLOGADO" -> "3";
			case "CONTABILIZADO" -> "4";
			case "PRESCRITO" -> "5";
			case "EM_APURACAO" -> "6";
			case "CANCELADO" -> "8";
			case "NAO_INICIALIZADO" -> "9";
			default -> null;
		};
	}

	private static String mapSituacaoConcursoCanal(String situacao) {
		return switch (situacao.toUpperCase(Locale.ROOT)) {
			case "ABERTO" -> "0";
			case "FECHADO" -> "1";
			default -> "1";
		};
	}

	private static String formatDateTime(JsonNode node, ZoneId zoneId) {
		if (node == null || node.isMissingNode() || node.isNull()) {
			return null;
		}
		String value = node.asText("");
		if (value.isBlank()) {
			return null;
		}
		LocalDateTime dateTime = LocalDateTime.parse(value, INPUT_DATE_TIME);
		return OUTPUT_DATE_TIME.format(dateTime.atZone(zoneId));
	}

	private static String decimalText(JsonNode node) {
		if (node == null || node.isMissingNode() || node.isNull()) {
			return null;
		}
		if (node.isTextual()) {
			return node.asText();
		}
		return node.decimalValue().stripTrailingZeros().toPlainString();
	}

	private static String valueAsString(JsonNode node) {
		if (node == null || node.isMissingNode() || node.isNull()) {
			return null;
		}
		if (node.isTextual()) {
			String text = node.asText();
			return text.isBlank() ? null : text;
		}
		return node.asText();
	}

	private static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}

	private static void appendAttr(StringBuilder attrs, String name, String value) {
		if (value == null || value.isBlank()) {
			return;
		}
		attrs.append(' ').append(name).append("=\"").append(XmlUtil.escape(value)).append('"');
	}

	private static void appendLine(StringBuilder xml, int indentLevel, String text) {
		xml.append(INDENT.repeat(indentLevel)).append(text).append('\n');
	}
}
