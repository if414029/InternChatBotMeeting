package com.groupbot.models.kaskus;

import java.util.List;

import com.groupbot.models.kaskus.KaskusSendList;

public class KaskusSend {
	private long id;
	private List<KaskusSendList> sendList;

	public KaskusSend(long id, List<KaskusSendList> sendList) {
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

	public List<KaskusSendList> getSendList() {
		return sendList;
	}

	public void setSendList(List<KaskusSendList> sendList) {
		this.sendList = sendList;
	}
}