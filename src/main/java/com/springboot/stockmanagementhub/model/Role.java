package com.springboot.stockmanagementhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name= "user_role")
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Role Title cannot be null")
    @Column(length = 20, nullable = false)
    private String roleTitle;
}

