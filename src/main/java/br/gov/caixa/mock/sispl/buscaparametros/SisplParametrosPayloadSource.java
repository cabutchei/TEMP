package br.gov.caixa.mock.sispl.buscaparametros;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

final class SisplParametrosPayloadSource {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private SisplParametrosPayloadSource() {
	}

	static ObjectNode load(MqConfig config) throws IOException {
		JsonNode root;
		if (config.jsonFile() != null) {
			root = MAPPER.readTree(config.jsonFile().toFile());
		} else {
			try (InputStream input = SisplParametrosPayloadSource.class
					.getResourceAsStream("/sample-parametros-simulacao.json")) {
				if (input == null) {
					throw new IOException("Bundled sample-parametros-simulacao.json not found.");
				}
				root = MAPPER.readTree(new String(input.readAllBytes(), StandardCharsets.UTF_8));
			}
		}
		return normalize(root, config);
	}

	static ObjectNode filter(ObjectNode normalized, long modalidade, long numeroConcurso) {
		ArrayNode parametros = MAPPER.createArrayNode();
		for (JsonNode parametro : normalized.withArray("parametros")) {
			JsonNode concurso = parametro.path("parametroJogo").path("concurso");
			long modalidadeId = resolveModalidadeId(concurso);
			long concursoNumero = resolveConcursoNumero(concurso);
			if (modalidadeId == modalidade && concursoNumero == numeroConcurso) {
				parametros.add(parametro.deepCopy());
			}
		}

		ObjectNode response = normalized.deepCopy();
		response.set("parametros", parametros);
		return response;
	}

	private static ObjectNode normalize(JsonNode rawRoot, MqConfig config) {
		JsonNode payload = rawRoot.path("payload").isMissingNode() ? rawRoot : rawRoot.path("payload");
		JsonNode parametrosNode = firstNonMissing(payload.path("parametros"), rawRoot.path("parametros"));
		ArrayNode parametros = MAPPER.createArrayNode();
		if (parametrosNode.isArray()) {
			parametros.addAll((ArrayNode) parametrosNode.deepCopy());
		}

		ObjectNode normalized = MAPPER.createObjectNode();
		normalized.put("canal", resolveCanal(rawRoot, payload, config));
		normalized.put("subcanal", resolveSubcanal(rawRoot, config));
		putString(normalized, "dataHoraAtualizacao",
				firstText(payload, "dataHoraAtualizacao", firstText(rawRoot, "dataHoraAtualizacao",
						firstText(rawRoot, "dataHoraServidor", null))));
		putNumber(normalized, "valorLimiteDiario",
				firstNumber(payload, "valorLimiteDiario", firstNumber(rawRoot, "valorLimiteDiario", 9999999L)));
		normalized.set("parametros", parametros);
		putNullable(normalized, "dataHoraFixaParaTestes",
				firstNonMissing(payload.path("dataFixaParaTestes"), rawRoot.path("dataHoraFixaParaTestes")));
		return normalized;
	}

	private static long resolveModalidadeId(JsonNode concurso) {
		JsonNode idNode = concurso.path("modalidadeDetalhada").path("id");
		if (idNode.isIntegralNumber()) {
			return idNode.longValue();
		}

		String modalidade = concurso.path("modalidade").asText("");
		return switch (modalidade) {
			case "MEGA_SENA" -> 2L;
			case "QUINA" -> 3L;
			case "SUPER_7" -> 7L;
			case "LOTOFACIL" -> 8L;
			case "MAIS_MILIONARIA" -> 9L;
			case "DIA_DE_SORTE" -> 11L;
			case "DUPLA_SENA" -> 18L;
			case "LOTECA" -> 19L;
			case "TIMEMANIA" -> 20L;
			default -> -1L;
		};
	}

	private static long resolveConcursoNumero(JsonNode concurso) {
		JsonNode numeroNode = firstNonMissing(concurso.path("numero"), concurso.path("concurso"));
		return numeroNode.isIntegralNumber() ? numeroNode.longValue() : -1L;
	}

	private static int resolveCanal(JsonNode rawRoot, JsonNode payload, MqConfig config) {
		JsonNode canalNode = firstNonMissing(rawRoot.path("canal"), payload.path("canal"));
		return canalNode.isIntegralNumber() ? canalNode.intValue() : config.apiCanal();
	}

	private static String resolveSubcanal(JsonNode rawRoot, MqConfig config) {
		String subcanal = firstText(rawRoot, "subcanal", null);
		return subcanal == null || subcanal.isBlank() ? config.apiSubcanal() : subcanal;
	}

	private static JsonNode firstNonMissing(JsonNode first, JsonNode second) {
		return first != null && !first.isMissingNode() && !first.isNull() ? first : second;
	}

	private static String firstText(JsonNode node, String field, String fallback) {
		JsonNode value = node.path(field);
		return value.isMissingNode() || value.isNull() ? fallback : value.asText();
	}

	private static long firstNumber(JsonNode node, String field, long fallback) {
		JsonNode value = node.path(field);
		return value.isIntegralNumber() ? value.longValue() : fallback;
	}

	private static void putString(ObjectNode node, String field, String value) {
		if (value == null) {
			node.putNull(field);
			return;
		}
		node.put(field, value);
	}

	private static void putNumber(ObjectNode node, String field, long value) {
		node.put(field, value);
	}

	private static void putNullable(ObjectNode node, String field, JsonNode value) {
		if (value == null || value.isMissingNode() || value.isNull()) {
			node.putNull(field);
			return;
		}
		node.set(field, value.deepCopy());
	}
}
