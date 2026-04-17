package br.gov.caixa.mock.sispl.buscaparametros;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

final class ResponsePayloadSource {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final DateTimeFormatter COMPACT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

	private ResponsePayloadSource() {
	}

	static String load(MqConfig config) throws IOException {
		String xml;
		if (config.responseFile() != null) {
			xml = Files.readString(config.responseFile(), StandardCharsets.UTF_8);
		} else if (config.jsonFile() != null) {
			JsonNode root = MAPPER.readTree(config.jsonFile().toFile());
			xml = ResponseXmlBuilder.build(root, config);
		} else {
			try (InputStream input = ResponsePayloadSource.class.getResourceAsStream("/sample-response.xml")) {
				if (input == null) {
					throw new IOException("Bundled sample-response.xml not found.");
				}
				xml = new String(input.readAllBytes(), StandardCharsets.UTF_8);
			}
		}

		OffsetDateTime now = OffsetDateTime.now(config.zoneId().getRules().getOffset(java.time.Instant.now()));
		Map<String, String> replacements = Map.of(
				"${HEADER_DATA_HORA}", COMPACT.format(now),
				"${ISO_TIMESTAMP}", ISO.format(now),
				"${COD_RETORNO}", Integer.toString(config.codRetorno()),
				"${ORIGEM_RETORNO}", XmlUtil.escape(config.origemRetorno()),
				"${MSG_RETORNO}", XmlUtil.escape(config.msgRetorno()),
				"${HEADER_USUARIO_SERVICO}", XmlUtil.escape(config.headerUserService()),
				"${HEADER_USUARIO}", XmlUtil.escape(config.headerUser()),
				"${HEADER_OPERACAO}", XmlUtil.escape(config.headerOperation()));

		String rendered = xml;
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			rendered = rendered.replace(entry.getKey(), entry.getValue());
		}
		return rendered;
	}
}
