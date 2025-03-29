package com.kp.eventmanagement.services;

public interface BookingService {

    public String bookTicket(Long eventId, String userName, String seatNumber);
    public String cancelTicket(Long eventId, Long bookingId);
    public void processEventBooking(String userName, Long eventId);
}
