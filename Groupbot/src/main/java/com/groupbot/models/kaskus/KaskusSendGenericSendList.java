package com.groupbot.models.kaskus;

import com.groupbot.models.kaskus.KaskusSendGenericBody;

public class KaskusSendGenericSendList {
	private KaskusSendGenericBody body;
	private String recipient;
	private String from;
	private String placeholder;
	
	public KaskusSendGenericSendList(KaskusSendGenericBody body, String recipient, String from, String placeholder) {
		super();
		this.body = body;
		this.recipient = recipient;
		this.from = from;
		this.placeholder = placeholder;
	}

	public KaskusSendGenericBody getBody() {
		return body;
	}
	
	public void setBody(KaskusSendGenericBody body) {
		this.body = body;
	}
	

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}