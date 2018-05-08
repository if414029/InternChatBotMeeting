package com.groupbot.models.kaskus;

import java.util.List;

import com.groupbot.models.kaskus.KaskusSendGenericSendList;

public class KaskusSendGeneric {
	private long id;
	private List<KaskusSendGenericSendList> sendList;
	
	public KaskusSendGeneric(long id, List<KaskusSendGenericSendList> sendList) {
		super();
		this.id = id;
		this.sendList = sendList;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<KaskusSendGenericSendList> getSendList() {
		return sendList;
	}
	
	public void setSendList(List<KaskusSendGenericSendList> sendList) {
		this.sendList = sendList;
	}
	
}