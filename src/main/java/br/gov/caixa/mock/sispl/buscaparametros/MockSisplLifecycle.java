package br.gov.caixa.mock.sispl.buscaparametros;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jakarta.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class MockSisplLifecycle {

	private static final Logger LOG = LoggerFactory.getLogger(MockSisplLifecycle.class);

	private ExecutorService executor;
	private MqResponder responder;

	void onStart(@Observes StartupEvent event) {
		MqConfig config = MqConfig.fromEnvironment();
		logConfig(config);

		if (!config.mqEnabled()) {
			LOG.info("MQ responder disabled by configuration. HTTP mock endpoints remain available.");
			return;
		}

		responder = new MqResponder(config);
		executor = Executors.newSingleThreadExecutor(runnable -> {
			Thread thread = new Thread(runnable, "sispl-mq-responder");
			thread.setDaemon(false);
			return thread;
		});

		executor.submit(() -> {
			try {
				responder.run();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				LOG.info("MQ responder interrupted.");
			}
		});
	}

	void onStop(@Observes ShutdownEvent event) {
		if (responder != null) {
			responder.stop();
		}
		if (executor != null) {
			executor.shutdownNow();
			try {
				if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
					LOG.warn("MQ responder executor did not stop within timeout.");
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void logConfig(MqConfig config) {
		LOG.info("Starting mock SISPL responder.");
		LOG.info("HTTP API: canal={} subcanal={}", config.apiCanal(), config.apiSubcanal());
		LOG.info("MQ enabled={}", config.mqEnabled());
		LOG.info("MQ: qmgr={} host={} port={} channel={} user={}",
				config.queueManager(), config.host(), config.port(), config.channel(), config.user());
		LOG.info("Queues: request={} response={} preferReplyTo={}",
				config.requestQueue(), config.responseQueue(), config.preferReplyTo());
		LOG.info("Payload source: responseFile={} jsonFile={} includeProximoConcurso={}",
				config.responseFile(), config.jsonFile(), config.includeProximoConcurso());
	}
}
