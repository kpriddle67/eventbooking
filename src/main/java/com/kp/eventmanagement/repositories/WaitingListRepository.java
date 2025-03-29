package com.kp.eventmanagement.repositories;
import com.kp.eventmanagement.model.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {

    // Custom query to get the waiting list for an event
    @Query("SELECT w FROM WaitingList w WHERE w.event.id = :eventId ORDER BY w.id ASC")
    List<WaitingList> findWaitingListByEventId(@Param("eventId") Long eventId);

    WaitingList findByUserNameAndEventId(String userName, Long eventId);

}

