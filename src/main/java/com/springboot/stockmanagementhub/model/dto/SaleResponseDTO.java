package com.springboot.stockmanagementhub.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleResponseDTO {
    private String productName;

    private Integer quantitySold;

    private LocalDateTime saleDate;

    private BigDecimal totalPrice;

}
