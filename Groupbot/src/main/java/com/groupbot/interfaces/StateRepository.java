package com.groupbot.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupbot.models.databases.Meeting;
import com.groupbot.models.databases.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	State findByIdUser(@Param("id_user") String idUser);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE State s SET event = :event WHERE id_state = :id_state")
    void editEvent(@Param("event") String event, @Param("id_state") Integer id_state);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE State s SET event = :event , idMeeting = :idMeeting WHERE id_state = :id_state")
    void editState(@Param("event") String event, @Param("idMeeting") Meeting idMeeting, @Param("id_state") Integer id_state);
}
