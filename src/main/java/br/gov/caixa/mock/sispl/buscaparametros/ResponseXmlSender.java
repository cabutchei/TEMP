package br.gov.caixa.mock.sispl.buscaparametros;

import java.nio.charset.Charset;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;

public final class ResponseXmlSender {

	private static final Logger LOG = LoggerFactory.getLogger(ResponseXmlSender.class);
	private static final int MQ_ID_LENGTH = 24;

	private ResponseXmlSender() {
	}

	public static void main(String[] args) throws Exception {
		MqConfig config = MqConfig.fromEnvironment();
		EnvConfig env = EnvConfig.load();

		String targetQueue = defaultValue(env.get("SISPL_SEND_QUEUE"), config.responseQueue());
		String messageIdHex = env.get("SISPL_SEND_MESSAGE_ID_HEX");
		String correlationIdHex = env.get("SISPL_SEND_CORRELATION_ID_HEX");
		String payload = ResponsePayloadSource.load(config);

		send(config, targetQueue, payload, messageIdHex, correlationIdHex);
	}

	static void send(MqConfig config, String targetQueue, String payload, String messageIdHex, String correlationIdHex)
			throws Exception {
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
		MQQueue queue = null;
		try {
			queueManager = new MQQueueManager(config.queueManager(), properties);
			queue = queueManager.accessQueue(targetQueue, MQConstants.MQOO_OUTPUT | MQConstants.MQOO_FAIL_IF_QUIESCING);

			MQMessage message = new MQMessage();
			message.format = MQConstants.MQFMT_STRING;
			message.characterSet = config.replyCcsid();
			message.encoding = config.replyEncoding();

			if (messageIdHex != null && !messageIdHex.isBlank()) {
				message.messageId = XmlUtil.fromHex(messageIdHex, MQ_ID_LENGTH);
			}
			if (correlationIdHex != null && !correlationIdHex.isBlank()) {
				message.correlationId = XmlUtil.fromHex(correlationIdHex, MQ_ID_LENGTH);
			} else if (messageIdHex != null && !messageIdHex.isBlank()) {
				message.correlationId = XmlUtil.fromHex(messageIdHex, MQ_ID_LENGTH);
			}

			Charset charset = XmlUtil.charsetForCcsid(config.replyCcsid());
			byte[] body = payload.getBytes(charset);
			message.write(body);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options = MQConstants.MQPMO_FAIL_IF_QUIESCING;
			queue.put(message, pmo);

			LOG.info("Sent XML payload to {} with messageId={} correlationId={} size={} bytes.",
					targetQueue, XmlUtil.toHex(message.messageId), XmlUtil.toHex(message.correlationId), body.length);
		} finally {
			closeQueue(queue);
			disconnect(queueManager);
		}
	}

	private static void closeQueue(MQQueue queue) {
		if (queue == null) {
			return;
		}
		try {
			queue.close();
		} catch (MQException e) {
			LOG.warn("Failed to close target queue.", e);
		}
	}

	private static void disconnect(MQQueueManager queueManager) {
		if (queueManager == null) {
			return;
		}
		try {
			queueManager.disconnect();
		} catch (MQException e) {
			LOG.warn("Failed to disconnect queue manager.", e);
		}
	}

	private static String defaultValue(String value, String defaultValue) {
		return value == null || value.isBlank() ? defaultValue : value.trim();
	}
}
