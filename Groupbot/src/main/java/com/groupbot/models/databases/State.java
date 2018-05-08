package com.groupbot.models.databases;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idState;
	private String idUser;
	private String event;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMeeting")
	private Meeting idMeeting;
	
	public State(String idUser, String event) {
		super();
		this.idUser = idUser;
		this.event = event;
	}

	public State(String idUser, String event, Meeting idMeeting) {
		super();
		this.idUser = idUser;
		this.event = event;
		this.idMeeting = idMeeting;
	}

	public State() {
		super();
	}

	public Integer getIdState() {
		return idState;
	}
	
	public void setIdState(Integer idState) {
		this.idState = idState;
	}
	
	public String getIdUser() {
		return idUser;
	}
	
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}

	public Meeting getIdMeeting() {
		return idMeeting;
	}

	public void setIdMeeting(Meeting idMeeting) {
		this.idMeeting = idMeeting;
	}
	
}