package com.kp.eventmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate eventDate;
    private Integer totalSeats;
    private Integer bookedSeats = 0;

    @ManyToOne
    @JoinColumn(name = "address_postal_code")
    private Address address;

//    @OneToMany(mappedBy = "event")
//    private List<Booking> bookings;
}

