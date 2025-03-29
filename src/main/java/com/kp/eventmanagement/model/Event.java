package com.kp.eventmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer totalSeats;
    private Integer bookedSeats = 0;

//    @OneToMany(mappedBy = "event")
//    private List<Booking> bookings;
}

