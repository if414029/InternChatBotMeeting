package com.groupbot.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.groupbot.models.kaskus.KaskusSendGeneric;
import com.groupbot.models.kaskus.KaskusSendGenericBody;
import com.groupbot.models.kaskus.KaskusSendGenericButton;
import com.groupbot.models.kaskus.KaskusSendGenericInteractive;
import com.groupbot.models.kaskus.KaskusSendGenericSendList;
import com.groupbot.models.kaskus.KaskusSend;
import com.groupbot.models.kaskus.KaskusSendList;
import com.groupbot.Constanta;
import com.groupbot.interfaces.DataRepository;
import com.groupbot.interfaces.MeetingRepository;
import com.groupbot.interfaces.StateRepository;
import com.groupbot.models.databases.Data;
import com.groupbot.models.databases.Meeting;
import com.groupbot.models.databases.State;
import com.groupbot.models.kaskus.Kaskus;

@RestController
public class KaskusController {
	@Autowired
	public RestTemplate restTemplate;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private MeetingRepository meetingRepository;
	@Autowired
	private DataRepository dataRepository;
	
	@RequestMapping(value= "/kaskus", method = RequestMethod.GET)
	public String getChallenge(HttpServletRequest request){
		String challenge = request.getParameter("hub.challenge");
		return challenge;
	}
	
	@RequestMapping(value= "/kaskus", method = RequestMethod.POST)
	public void getMessage(@RequestBody Kaskus kaskus) throws ParseException{
		String kata = kaskus.getFrom();
		String[] idUser = kata.split("/");
		if(stateRepository.findByIdUser(idUser[1]) == null){
			userNull(kaskus, idUser[1]);
		}else{
			String event = stateRepository.findByIdUser(idUser[1]).getEvent().toString();
			Integer id_state = stateRepository.findByIdUser(idUser[1]).getIdState();
			if(event.equalsIgnoreCase("NULL")){
				stateNull(kaskus, id_state);
			}else if(event.equalsIgnoreCase("Menu")){
				stateMenu(kaskus, id_state);
			}else if(event.equalsIgnoreCase("Guide")){
				stateGuide(kaskus, id_state);
			}else if(event.equalsIgnoreCase("CreateMeeting")){
				sendDetailMeeting(kaskus);
				stateRepository.editEvent("DetailMeeting", id_state);
			}else if(event.equalsIgnoreCase("DetailMeeting")){
				sendMessageMeeting(kaskus);
				sendMessageChoice(kaskus);
				stateRepository.editEvent("Choise", id_state);
			}else if(event.equalsIgnoreCase("ListMeeting")){
				stateListMeeting(kaskus, id_state, idUser[1]);
			}else if(event.equalsIgnoreCase("Choise")){
				stateChoise(kaskus, id_state, idUser[1]);
			}else if(event.equalsIgnoreCase("ChoiseMeeting")){
				stateChoiseMeeting(kaskus, id_state, idUser[1]);
			}
		}	
	}
	
	public void sendMessageGuide(Kaskus kaskus){
		List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
		
		KaskusSendGenericButton kaskusSendGenericButton1 = new KaskusSendGenericButton("create_meeting", "Create Meeting", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton1);
		KaskusSendGenericButton kaskusSendGenericButton2 = new KaskusSendGenericButton("list_meeting", "List Meeting", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton2);
		KaskusSendGenericButton kaskusSendGenericButton3 = new KaskusSendGenericButton("cancel", "Cancel", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton3);
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, null,
				null, "Daftar Menu yang tersedia :");
		
		kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+Constanta.DOMAIN,
				Constanta.FROM, Constanta.PLACEHOLDER);
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(Constanta.ID_BOT, kaskusSendGenericSendLists);
		
		HttpHeaders headers = getHeaders();

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendDataPresence(Kaskus kaskus, Meeting idMeeting){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		
		List<Data> data1 = dataRepository.findAllByIdMeetingAndOption(idMeeting, "Ya");
		List<Data> data2 = dataRepository.findAllByIdMeetingAndOption(idMeeting, "Tidak");
		KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
				"Data kehadiran : \n" + 
				"Hadir : " + data1.size() + " Orang\n" +  
				"Tidak : " + data2.size() + " Orang");
		kaskusSendLists.add(kaskusSendList);
		KaskusSend kaskusSend = new KaskusSend(Constanta.ID_BOT, kaskusSendLists);
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendMessageMeeting(Kaskus kaskus) throws ParseException{
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		String kata = kaskus.getFrom();
		String[] idUsername = kata.split("/");
		String data = kaskus.getBody();
		String[] details = data.split(",");
		
		List<Meeting> meetings = meetingRepository.findAllByIdUser(idUsername[1]); 
		Integer idMeeting = meetings.get(meetings.size()-1).getIdMeeting();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(details[1]);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        Date currentDate = new Date();
        if(sql.compareTo(currentDate) < 0){
        	KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
    				"Tanggal harus lebih besar dari hari ini.");
        	kaskusSendLists.add(kaskusSendList);
        }else{
        	meetingRepository.updateMeeting(details[0], sql, details[2], "Not Yet", idMeeting);
        	KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
    				"Selamat Data Berhasil Dibuat");
        	kaskusSendLists.add(kaskusSendList);
        }
		
		KaskusSend kaskusSend = new KaskusSend(Constanta.ID_BOT, kaskusSendLists);
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendMessageTitleMeeting(Kaskus kaskus){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
				"Silahkan masukkan judul Meeting\n\n"
				+ "Example : Spring Rest Docs");
		kaskusSendLists.add(kaskusSendList);
		KaskusSend kaskusSend = new KaskusSend(Constanta.ID_BOT, kaskusSendLists);
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendDetailMeeting(Kaskus kaskus){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		String kata = kaskus.getFrom();
		String[] idUser = kata.split("/");
		Meeting meeting = new Meeting(idUser[1], kaskus.getBody(), kaskus.getTo());
		meetingRepository.save(meeting);
		
		KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
				"Silahkan masukkan Tempat, Tanggal, dan Waktu Meeting\n"
				+ "Example : Menara Citicon 17th Ruang Aquarium,2017-08-25,10am - 11am");
		kaskusSendLists.add(kaskusSendList);
		KaskusSend kaskusSend = new KaskusSend(Constanta.ID_BOT, kaskusSendLists);
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendListMeeting(Kaskus kaskus){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		
		for(int i=0; i<meetingRepository.findAll().size(); i++){
			if(meetingRepository.findAll().size() == i){
				break;
			}else {
				int no = i + 1;
				KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+Constanta.DOMAIN, 
						no + ". [Title : " + meetingRepository.findAll().get(i).getTitle()
						+ "] [Place : " + meetingRepository.findAll().get(i).getPlace()
						+ "] [Date : " + meetingRepository.findAll().get(i).getDate()
						+ "] [Time : " + meetingRepository.findAll().get(i).getTime() + "]" + "\n");
				kaskusSendLists.add(kaskusSendList);
			}
		}
		
		KaskusSend kaskusSend = new KaskusSend(Constanta.ID_BOT, kaskusSendLists);
		HttpHeaders headers = getHeaders();
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendMessageMenu(Kaskus kaskus){
		List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
		
		KaskusSendGenericButton kaskusSendGenericButton = new KaskusSendGenericButton("Guide", "Guide", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton);
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, Constanta.IMAGE,
				"Test", "Testing Bot");
		
		kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+Constanta.DOMAIN,
				Constanta.FROM, Constanta.PLACEHOLDER);
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(Constanta.ID_BOT, kaskusSendGenericSendLists);
		
		HttpHeaders headers = getHeaders();

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void sendMessageChoice(Kaskus kaskus){
		List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
		
		KaskusSendGenericButton kaskusSendGenericButton1 = new KaskusSendGenericButton("Ya", "Ya", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton1);
		KaskusSendGenericButton kaskusSendGenericButton2 = new KaskusSendGenericButton("Tidak", "Tidak", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton2);
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, null,
				null, "Apakah anda ingin mengikuti meeting ini?");
		
		kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+Constanta.DOMAIN,
				Constanta.FROM, Constanta.PLACEHOLDER);
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(Constanta.ID_BOT, kaskusSendGenericSendLists);
		
		HttpHeaders headers = getHeaders();

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		restTemplate.postForEntity(Constanta.KASKUS_KEY, entity1, String.class);
	}
	
	public void userNull(Kaskus kaskus, String idUser){
		if(kaskus.getBody().equalsIgnoreCase("Menu")){
			State states = new State(idUser, "Menu");
			stateRepository.save(states);
			sendMessageMenu(kaskus);
		}
	}
	
	public void stateNull(Kaskus kaskus, Integer id_state){
		if(kaskus.getBody().equalsIgnoreCase("Menu")){
			sendMessageMenu(kaskus);
			stateRepository.editEvent("Menu", id_state);
		}else{
			stateRepository.editEvent("NULL", id_state);
		}
	}
	
	public void stateMenu(Kaskus kaskus, Integer id_state){
		if(kaskus.getBody().equalsIgnoreCase("Guide")){
			System.out.println("Tes");
			sendMessageGuide(kaskus);
			stateRepository.editEvent("Guide", id_state);
		}else{
			
		}
	}
	
	public void stateGuide(Kaskus kaskus, Integer id_state){
		if(kaskus.getBody().equalsIgnoreCase("create_meeting")){
			sendMessageTitleMeeting(kaskus);
			stateRepository.editEvent("CreateMeeting", id_state);
		}else if(kaskus.getBody().equalsIgnoreCase("list_meeting")){
			sendListMeeting(kaskus);
			stateRepository.editEvent("ListMeeting", id_state);
		}else if(kaskus.getBody().equalsIgnoreCase("cancel")){
			stateRepository.editEvent("NULL", id_state);
		}
	}
	
	public void stateListMeeting(Kaskus kaskus, Integer id_state, String idUser){
		List<Meeting> meetings = meetingRepository.findAll();
		Integer meeting = Integer.parseInt(kaskus.getBody());
		Meeting idMeeting = meetings.get(meeting - 1);
		if(dataRepository.findByIdUserAndIdMeeting(idUser, idMeeting) == null){
			sendDataPresence(kaskus, idMeeting);
			sendMessageChoice(kaskus);
			stateRepository.editState("ChoiseMeeting", idMeeting, id_state);
		}else{
			sendDataPresence(kaskus, idMeeting);
			stateRepository.editEvent("NULL", id_state);
		}
	}
	
	public void stateChoise(Kaskus kaskus, Integer id_state, String idUser){
		List<Meeting> meetings = meetingRepository.findAllByIdUser(idUser); 
		Meeting idMeeting = meetingRepository.findOne(meetings.get(meetings.size()-1).getIdMeeting());
		if(kaskus.getBody().equalsIgnoreCase("Ya")){
			Data data = new Data(idUser, kaskus.getBody(), idMeeting);
			dataRepository.save(data);
			stateRepository.editEvent("NULL", id_state);
		}else{
			Data data = new Data(idUser, kaskus.getBody(), idMeeting);
			dataRepository.save(data);
			stateRepository.editEvent("NULL", id_state);
		}
	}
	
	public void stateChoiseMeeting(Kaskus kaskus, Integer id_state, String idUser){
		Meeting idMeeting = stateRepository.findByIdUser(idUser).getIdMeeting();
		if(kaskus.getBody().equalsIgnoreCase("Ya")){
			Data data = new Data(idUser, kaskus.getBody(), idMeeting);
			dataRepository.save(data);
			stateRepository.editState("NULL", null, id_state);
		}else{
			Data data = new Data(idUser, kaskus.getBody(), idMeeting);
			dataRepository.save(data);
			stateRepository.editState("NULL", null, id_state);
		}
	}
	
	public HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		
		headers.add(HttpHeaders.AUTHORIZATION, Constanta.AUTHORIZATION);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		return headers;
	}
	
}
