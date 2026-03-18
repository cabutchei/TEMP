package br.gov.caixa.dominio;

import java.io.Serializable;

import br.gov.caixa.util.StringUtil;


public class QueueStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeFila;
	
	private Throwable exception;
	
	private Integer defPersistence;
	private Integer defPriority;
	private Integer maxQDepth;
	private Integer currentQDepth;
	private Integer inhibitGet;
	private Integer inhibitPut;
	private Integer maxMsgLength;
	private Integer msgDeliverySequence;
	private Integer queueType;
	private Integer shareability;
	private Integer uncommittedMsgs;

	private final String nomeJNDI;
	
	public QueueStatus(String nomeFila, String nomeJNDI) {
		this.nomeFila = nomeFila;
		this.nomeJNDI = nomeJNDI;
	}
	
	public QueueStatus(String nomeJNDI, Throwable exception) {
		this.nomeJNDI = nomeJNDI;
		this.exception = exception;
	}

	public String getNomeFila() {
		return nomeFila;
	}

	public Throwable getException() {
		return exception;
	}

	public Integer getDefPersistence() {
		return defPersistence;
	}

	public void setDefPersistence(Integer defPersistence) {
		this.defPersistence = defPersistence;
	}

	public Integer getDefPriority() {
		return defPriority;
	}

	public void setDefPriority(Integer defPriority) {
		this.defPriority = defPriority;
	}

	public Integer getMaxQDepth() {
		return maxQDepth;
	}

	public void setMaxQDepth(Integer maxQDepth) {
		this.maxQDepth = maxQDepth;
	}

	public Integer getCurrentQDepth() {
		return currentQDepth;
	}

	public void setCurrentQDepth(Integer currentQDepth) {
		this.currentQDepth = currentQDepth;
	}

	public Integer getInhibitGet() {
		return inhibitGet;
	}

	public void setInhibitGet(Integer inhibitGet) {
		this.inhibitGet = inhibitGet;
	}

	public Integer getInhibitPut() {
		return inhibitPut;
	}

	public void setInhibitPut(Integer inhibitPut) {
		this.inhibitPut = inhibitPut;
	}

	public Integer getMaxMsgLength() {
		return maxMsgLength;
	}

	public void setMaxMsgLength(Integer maxMsgLength) {
		this.maxMsgLength = maxMsgLength;
	}

	public Integer getMsgDeliverySequence() {
		return msgDeliverySequence;
	}

	public void setMsgDeliverySequence(Integer msgDeliverySequence) {
		this.msgDeliverySequence = msgDeliverySequence;
	}

	public Integer getQueueType() {
		return queueType;
	}

	public void setQueueType(Integer queueType) {
		this.queueType = queueType;
	}

	public Integer getShareability() {
		return shareability;
	}

	public void setShareability(Integer shareability) {
		this.shareability = shareability;
	}

	public void setNomeFila(String nomeFila) {
		this.nomeFila = nomeFila;
	}

	public boolean isErroAcesso() {
		return exception != null;
	}
	
	@Override
	public String toString() {
		int initialStringBuilderCapacity = 200;
		StringBuilder builder = new StringBuilder(initialStringBuilderCapacity);
		builder.append("QueueStatus [nomeFila=");
		builder.append(nomeFila);
		builder.append(", exception=");
		builder.append(exception);
		builder.append(", defPersistence=");
		builder.append(defPersistence);
		builder.append(", defPriority=");
		builder.append(defPriority);
		builder.append(", maxQDepth=");
		builder.append(maxQDepth);
		builder.append(", currentQDepth=");
		builder.append(currentQDepth);
		builder.append(", inhibitGet=");
		builder.append(inhibitGet);
		builder.append(", inhibitPut=");
		builder.append(inhibitPut);
		builder.append(", maxMsgLength=");
		builder.append(maxMsgLength);
		builder.append(", msgDeliverySequence=");
		builder.append(msgDeliverySequence);
		builder.append(", queueType=");
		builder.append(queueType);
		builder.append(", shareability=");
		builder.append(shareability);
		builder.append(']');
		return builder.toString();
	}

	public String getNomeJndiPorNome() {
		if(StringUtil.isEmpty(nomeFila)) {
			return nomeJNDI+"/"+nomeFila;
		}
		return nomeJNDI;
	}

	public String getNomeJNDI() {
		return nomeJNDI;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeJNDI == null) ? 0 : nomeJNDI.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QueueStatus)) {
			return false;
		}
		QueueStatus other = (QueueStatus) obj;
		if (nomeJNDI == null) {
			if (other.nomeJNDI != null) {
				return false;
			}
		} else if (!nomeJNDI.equals(other.nomeJNDI)) {
			return false;
		}
		return true;
	}

	public Integer getUncommittedMsgs() {
		return uncommittedMsgs;
	}

	public void setUncommittedMsgs(Integer uncommittedMsgs) {
		this.uncommittedMsgs = uncommittedMsgs;
	}
	
}
