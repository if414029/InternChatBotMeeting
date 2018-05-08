package com.groupbot.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupbot.models.databases.Data;
import com.groupbot.models.databases.Meeting;


@Repository
public interface DataRepository extends JpaRepository<Data, Integer>{
	List<Data> findAllByIdMeetingAndOption(@Param("id_meeting") Meeting idMeeting, @Param("option") String option);
	
	Data findByIdUserAndIdMeeting(@Param("id_user") String idUser, @Param("id_meeting") Meeting idMeeting);
}
