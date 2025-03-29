package com.kp.eventmanagement.services;

import com.kp.eventmanagement.model.Event;
import com.kp.eventmanagement.model.WaitingList;

import java.util.List;

public interface EventService {

    public Event createEvent(Event event);
    public Integer getAvailableSeats(Long eventId);
    public List<WaitingList> getWaitingListForEvent(Long eventId) ;
    public List<Event> getAllEvents() ;

}
