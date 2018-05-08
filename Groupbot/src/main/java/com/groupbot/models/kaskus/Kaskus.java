package com.groupbot.models.kaskus;

public class Kaskus {
	private String from;
	private String fromPlain;
	private String to;
	private String stanza;
	private String body;
	private String client;
	private String timestamp;
	private String messageId;
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFromPlain() {
		return fromPlain;
	}
	
	public void setFromPlain(String fromPlain) {
		this.fromPlain = fromPlain;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getStanza() {
		return stanza;
	}
	
	public void setStanza(String stanza) {
		this.stanza = stanza;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getClient() {
		return client;
	}
	
	public void setClient(String client) {
		this.client = client;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "Kaskus [from=" + from + ", fromPlain=" + fromPlain + ", to=" + to + ", stanza=" + stanza + ", body="
				+ body + ", client=" + client + ", timestamp=" + timestamp + ", messageId=" + messageId + "]";
	}
	
}
