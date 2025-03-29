package com.kp.eventmanagement.services;

import com.kp.eventmanagement.model.Booking;
import com.kp.eventmanagement.model.Event;
import com.kp.eventmanagement.model.WaitingList;
import com.kp.eventmanagement.repositories.BookingRepository;
import com.kp.eventmanagement.repositories.EventRepository;
import com.kp.eventmanagement.repositories.WaitingListRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WaitingListRepository waitingListRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public Event createEvent(Event event) {
        event.setEventDate(LocalDate.now());
        return eventRepository.save(event);
    }

    public Integer getAvailableSeats(Long eventId) {
        return eventRepository.findAvailableSeats(eventId);
    }

    public List<WaitingList> getWaitingListForEvent(Long eventId) {
        return waitingListRepository.findWaitingListByEventId(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.listAllEvents();
    }

    public List<Booking> getAllBookings(Long eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    public void eventBookingFallback(Event event, Throwable t) {
        System.out.println("Booking rate limit exceeded. Please try again later.");
    }

}
