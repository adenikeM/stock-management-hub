package com.springboot.stockmanagementhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.ToString;

@Entity
@Table(name= "user_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String address;

    @NotEmpty
    @Column(nullable = false)
    private String city;
}
