package com.kp.eventmanagement.controller;

import com.kp.eventmanagement.model.Booking;
import com.kp.eventmanagement.model.Event;
import com.kp.eventmanagement.model.WaitingList;
import com.kp.eventmanagement.services.BookingServiceImpl;
import com.kp.eventmanagement.services.EventServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;


    @RateLimiter(name = "eventBookingRateLimiter", fallbackMethod = "eventBookingFallback")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {

        eventServiceImpl.createEvent(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    private String eventBookingFallback(Event event, Throwable t) {
        return "Booking rate limit exceeded. Please try again later.";
    }

    @PostMapping("/{eventId}/book")
    public ResponseEntity<String> bookTicket(@PathVariable Long eventId, @RequestHeader(name="x-username", required = true) String userName, @RequestParam String seatNumber) {
        String response = bookingServiceImpl.bookTicket(eventId, userName, seatNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long eventId, @RequestParam Long bookingId) {
        String response = bookingServiceImpl.cancelTicket(eventId, bookingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/availableSeats")
    public Integer getAvailableSeats(@PathVariable Long eventId) {

        return eventServiceImpl.getAvailableSeats(eventId);
    }

    @GetMapping("/{eventId}/waitingList")
    public List<WaitingList> getWaitingList(@PathVariable Long eventId) {
        return eventServiceImpl.getWaitingListForEvent(eventId);
    }

    @GetMapping
    public List<Event> getAllEvents() {

        return eventServiceImpl.getAllEvents();
    }

    @GetMapping("/{eventId}/bookings")
    public List<Booking> getAllBookings(@PathVariable Long eventId) {

        return eventServiceImpl.getAllBookings(eventId);
    }

}

