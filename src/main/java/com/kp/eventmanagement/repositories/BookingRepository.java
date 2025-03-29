package com.kp.eventmanagement.repositories;


import com.kp.eventmanagement.model.Booking;
import com.kp.eventmanagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings for a particular event
    List<Booking> findByEventId(Long eventId);

    @Query("SELECT b FROM Booking b WHERE b.event.id = :eventId AND b.id = :bookingId")
    Booking findBookingById(Long eventId, Long bookingId);

    // Find bookings by seat number (optional)
    List<Booking> findBySeatNumber(String seatNumber);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.event.id = :eventId")
    long countBookedSeats(@Param("eventId") Long eventId);
}
