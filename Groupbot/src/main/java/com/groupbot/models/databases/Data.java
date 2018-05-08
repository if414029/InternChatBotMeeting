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
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idData;
	private String idUser;
	private String option;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMeeting")
	private Meeting idMeeting;

	public Data(String idUser, String option, Meeting idMeeting) {
		super();
		this.idUser = idUser;
		this.option = option;
		this.idMeeting = idMeeting;
	}

	public Data(Meeting idMeeting) {
		super();
		this.idMeeting = idMeeting;
	}

	public Data() {
		super();
	}

	public Integer getIdData() {
		return idData;
	}

	public void setIdData(Integer idData) {
		this.idData = idData;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Meeting getIdMeeting() {
		return idMeeting;
	}

	public void setIdMeeting(Meeting idMeeting) {
		this.idMeeting = idMeeting;
	}
	
}
