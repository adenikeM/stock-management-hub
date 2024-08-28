package com.springboot.stockmanagementhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "customer_info")
public class CustomerInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;
}
