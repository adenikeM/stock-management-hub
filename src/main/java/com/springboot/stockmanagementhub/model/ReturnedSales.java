package com.springboot.stockmanagementhub.model;

import com.springboot.stockmanagementhub.model.enumeration.ReturnReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReturnedSales implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @Getter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 20, nullable = false)
    private Integer quantityReturned;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnReason reasonForReturn;

    @Column(length = 200)
    private String additionalComments;

    private LocalDate returnDate = LocalDate.now();

}
