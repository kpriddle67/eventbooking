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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WaitingListRepository waitingListRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RateLimiter(name = "eventBookingRateLimiter", fallbackMethod = "eventBookingFallback")
    @Transactional
    public String bookTicket(Long eventId, String userName, String seatNumber) {
        String response = "";
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getBookedSeats() < event.getTotalSeats()) {
            Booking booking = new Booking();
            booking.setUserName(userName);
            booking.setSeatNumber(seatNumber);
            booking.setEvent(event);
            booking.setBookingDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            bookingRepository.save(booking);
            System.out.println("Date should be: " + LocalDate.of(2025,1,21));
            event.setBookedSeats(event.getBookedSeats() + 1);
            eventRepository.save(event);
            processEventBooking(userName, eventId);
            response = "Booked";

        } else {
            // Add user to waiting list if the event is fully booked
            WaitingList existingWaitingListEntry = waitingListRepository.findByUserNameAndEventId(userName, eventId);
            if (existingWaitingListEntry == null) {
                WaitingList waitingList = new WaitingList();
                waitingList.setUserName(userName);
                waitingList.setDateAdded(LocalDateTime.now());
                waitingList.setEvent(event);
                waitingListRepository.save(waitingList);
                notifyWaitingList(eventId, userName);
                response = "WaitingList";
            }
        }
        return response;
    }


    public String cancelTicket(Long eventId, Long bookingId) {
        Booking booking = bookingRepository.findBookingById(eventId,bookingId);
        bookingRepository.delete(booking);
        return "booking cancelled for eventId " + eventId + " and bookingId " + bookingId;
    }

    private void notifyWaitingList(Long eventId, String userName) {

        System.out.println("Event fully booked. " + userName + " added to the waiting list for event " + eventId);
    }

    public void eventBookingFallback(Event event, Throwable t) {
        System.out.println("Booking rate limit exceeded. Please try again later.");
    }

    public void processEventBooking(String userName, Long eventId) {
        taskExecutor.submit(() -> {
            // Processing the event bookings in a separate thread (e.g., notifying users)
            System.out.println("Thankyou for booking. An email or sms could now be sent to " + userName + " as confirmation of booking onto event " + eventId);
            System.out.println("Processing event booking...");
        });
    }
}



