package com.kp.eventmanagement.repositories;
import com.kp.eventmanagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Custom query to find available seats for a specific event
    @Query("SELECT e.totalSeats - e.bookedSeats FROM Event e WHERE e.id = :eventId")
    Integer findAvailableSeats(@Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e")
    List<Event> listAllEvents();


    @Query("SELECT e.id, e.name, e.description, b.event FROM Event e LEFT JOIN Booking b ON e.id = b.id")
    List<Event> listAllEventsWithBookings();
}

