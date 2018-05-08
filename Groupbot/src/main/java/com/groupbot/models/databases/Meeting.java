package com.groupbot.models.databases;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "meeting")
public class Meeting {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idMeeting;
	private String idUser;
	private String title;
	private String place;
	private String time;
	private Date date;
	private String idGroup;
	private String status;
	
	@OneToMany(mappedBy = "idMeeting")
	private Set<Data> datas;

	public Meeting(String idUser, String title, String idGroup) {
		super();
		this.idUser = idUser;
		this.title = title;
		this.idGroup = idGroup;
	}

	public Meeting() {
		super();
	}

	public Meeting(Integer idMeeting, String place, Date date, String time, String status) {
		super();
		this.idMeeting = idMeeting;
		this.place = place;
		this.date = date;
		this.time = time;
		this.status = status;
	}

	public Meeting(String idUser) {
		super();
		this.idUser = idUser;
	}

	public Integer getIdMeeting() {
		return idMeeting;
	}
	
	public void setIdMeeting(Integer idMeeting) {
		this.idMeeting = idMeeting;
	}
	
	public String getIdUser() {
		return idUser;
	}
	
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "[Title : " + title + "] [Place : " + place + "] [Date : " + date + "] [Time : " + time + "]" + "\n" ;
	}
	
	
	
}
