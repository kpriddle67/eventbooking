package com.kp.eventmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WaitingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;

    @ManyToOne
    private Event event;
}
