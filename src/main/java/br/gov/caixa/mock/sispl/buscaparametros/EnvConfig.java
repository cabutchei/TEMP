package br.gov.caixa.mock.sispl.buscaparametros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class EnvConfig {

	private static final Logger LOG = LoggerFactory.getLogger(EnvConfig.class);
	private static final String ENV_FILE_NAME = ".env";

	private final Map<String, String> values;

	private EnvConfig(Map<String, String> values) {
		this.values = values;
	}

	static EnvConfig load() {
		Path envFile = Path.of("").toAbsolutePath().resolve(ENV_FILE_NAME);
		Map<String, String> values = new HashMap<>();

		if (Files.exists(envFile)) {
			try {
				for (String line : Files.readAllLines(envFile)) {
					String trimmed = line.trim();
					if (trimmed.isEmpty() || trimmed.startsWith("#")) {
						continue;
					}

					int separatorIndex = trimmed.indexOf('=');
					if (separatorIndex <= 0) {
						continue;
					}

					String key = trimmed.substring(0, separatorIndex).trim();
					String value = trimmed.substring(separatorIndex + 1).trim();
					values.put(key, stripQuotes(value));
				}
				LOG.info("Loaded configuration from {}.", envFile);
			} catch (IOException e) {
				throw new IllegalStateException("Failed to read " + envFile, e);
			}
		} else {
			LOG.info("No .env file found at {}. Falling back to process environment.", envFile);
		}

		return new EnvConfig(values);
	}

	String get(String key) {
		String envValue = System.getenv(key);
		if (envValue != null && !envValue.isBlank()) {
			return envValue.trim();
		}
		String fileValue = values.get(key);
		return fileValue == null || fileValue.isBlank() ? null : fileValue.trim();
	}

	boolean getBoolean(String key, boolean defaultValue) {
		String value = get(key);
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		return switch (value.trim().toLowerCase(Locale.ROOT)) {
			case "1", "true", "yes", "y", "on" -> true;
			case "0", "false", "no", "n", "off" -> false;
			default -> defaultValue;
		};
	}

	int getInt(String key, int defaultValue) {
		String value = get(key);
		return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value.trim());
	}

	Path getPath(String key) {
		String value = get(key);
		return value == null || value.isBlank() ? null : Path.of(value.trim());
	}

	private static String stripQuotes(String value) {
		if (value.length() >= 2) {
			if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
				return value.substring(1, value.length() - 1);
			}
		}
		return value;
	}
}
