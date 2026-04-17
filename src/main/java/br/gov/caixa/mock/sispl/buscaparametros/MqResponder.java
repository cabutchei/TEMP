package br.gov.caixa.mock.sispl.buscaparametros;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;

final class MqResponder {

	private static final Logger LOG = LoggerFactory.getLogger(MqResponder.class);

	private final MqConfig config;
	private volatile boolean running = true;

	MqResponder(MqConfig config) {
		this.config = config;
	}

	void run() throws InterruptedException {
		while (running) {
			try {
				runLoop();
			} catch (Exception e) {
				if (!running) {
					return;
				}
				LOG.error("MQ responder loop failed. Reconnecting in {} ms.", config.reconnectDelayMillis(), e);
				Thread.sleep(config.reconnectDelayMillis());
			}
		}
	}

	void stop() {
		running = false;
	}

	private void runLoop() throws MQException, IOException {
		Hashtable<String, Object> properties = new Hashtable<>();
		properties.put(CMQC.HOST_NAME_PROPERTY, config.host());
		properties.put(CMQC.PORT_PROPERTY, config.port());
		properties.put(CMQC.CHANNEL_PROPERTY, config.channel());
		properties.put(CMQC.USER_ID_PROPERTY, config.user());
		properties.put(CMQC.PASSWORD_PROPERTY, config.password());
		properties.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);
		properties.put(CMQC.USE_MQCSP_AUTHENTICATION_PROPERTY, Boolean.TRUE);

		MQEnvironment.disableTracing();

		MQQueueManager queueManager = null;
		MQQueue requestQueue = null;
		try {
			queueManager = new MQQueueManager(config.queueManager(), properties);
			requestQueue = queueManager.accessQueue(
					config.requestQueue(),
					MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_INQUIRE);
			LOG.info("Connected to QMGR {} at {}:{} channel {}. Waiting on {}.",
					config.queueManager(), config.host(), config.port(), config.channel(), config.requestQueue());

			int processed = 0;
			while (running) {
				MQMessage request = new MQMessage();
				MQGetMessageOptions gmo = new MQGetMessageOptions();
				gmo.options = MQConstants.MQGMO_WAIT | MQConstants.MQGMO_FAIL_IF_QUIESCING;
				gmo.waitInterval = config.pollWaitMillis();

				try {
					requestQueue.get(request, gmo);
				} catch (MQException e) {
					if (e.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
						if (!running) {
							return;
						}
						if (config.runOnce() && processed > 0) {
							LOG.info("Run-once mode completed after {} message(s).", processed);
							return;
						}
						continue;
					}
					throw e;
				}

				processed++;
				handleRequest(queueManager, request);

				if (config.runOnce()) {
					LOG.info("Run-once mode completed after {} message(s).", processed);
					return;
				}
			}
		} finally {
			closeQueue(requestQueue, "requestQueue");
			disconnect(queueManager);
		}
	}

	private void handleRequest(MQQueueManager queueManager, MQMessage request) throws IOException, MQException {
		String requestBody = readBody(request);
		String requestMsgIdHex = XmlUtil.toHex(request.messageId);
		String replyQueueName = resolveReplyQueue(request);
		String responseXml = ResponsePayloadSource.load(config);

		LOG.info("Received request messageId={} format={} ccsid={} replyTo={} bytes={}",
				requestMsgIdHex, request.format, request.characterSet, replyQueueName, request.getMessageLength());
		LOG.debug("Request payload:\n{}", requestBody);

		MQQueue responseQueue = null;
		try {
			responseQueue = queueManager.accessQueue(
					replyQueueName,
					MQConstants.MQOO_OUTPUT | MQConstants.MQOO_FAIL_IF_QUIESCING);
			MQMessage response = new MQMessage();
			response.format = MQConstants.MQFMT_STRING;
			response.characterSet = config.replyCcsid();
			response.encoding = config.replyEncoding();
			response.messageId = Arrays.copyOf(request.messageId, request.messageId.length);
			response.correlationId = Arrays.copyOf(request.messageId, request.messageId.length);

			byte[] body = responseXml.getBytes(XmlUtil.charsetForCcsid(config.replyCcsid()));
			response.write(body);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options = MQConstants.MQPMO_FAIL_IF_QUIESCING;
			responseQueue.put(response, pmo);

			LOG.info("Sent response to {} with messageId={} correlationId={} size={} bytes.",
					replyQueueName, XmlUtil.toHex(response.messageId), XmlUtil.toHex(response.correlationId), body.length);
		} finally {
			closeQueue(responseQueue, "responseQueue");
		}
	}

	private String resolveReplyQueue(MQMessage request) {
		if (config.preferReplyTo() && request.replyToQueueName != null && !request.replyToQueueName.isBlank()) {
			return request.replyToQueueName.trim();
		}
		return config.responseQueue();
	}

	private String readBody(MQMessage request) throws IOException {
		byte[] payload = new byte[request.getDataLength()];
		request.readFully(payload);
		Charset charset = XmlUtil.charsetForCcsid(request.characterSet);
		return new String(payload, charset);
	}

	private void closeQueue(MQQueue queue, String label) {
		if (queue == null) {
			return;
		}
		try {
			queue.close();
		} catch (MQException e) {
			LOG.warn("Failed to close {}.", label, e);
		}
	}

	private void disconnect(MQQueueManager queueManager) {
		if (queueManager == null) {
			return;
		}
		try {
			queueManager.disconnect();
		} catch (MQException e) {
			LOG.warn("Failed to disconnect queue manager.", e);
		}
	}
}
