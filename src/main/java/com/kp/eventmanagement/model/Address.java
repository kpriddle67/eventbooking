package com.kp.eventmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {

    private String Address1;
    private String Address2;
    private String City;
    private String State;
    private String Country;
    @Id
    private String PostalCode;


}
