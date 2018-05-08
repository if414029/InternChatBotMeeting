package com.groupbot.interfaces;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.groupbot.models.databases.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{
	Meeting findByIdUser(@Param("id_user") String idUser);
	
	List<Meeting> findAllByIdUser(@Param("id_user") String idUser);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Meeting m SET place = :place , date =:date, time =:time , status =:status WHERE id_meeting = :id_meeting ORDER BY id_meeting DESC")
    void updateMeeting(@Param("place") String place, @Param("date") Date date, @Param("time") String time, @Param("status") String status, @Param("id_meeting") Integer id_meeting);
}
