package com.springboot.stockmanagementhub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductReturn {
    private Long productId;
    private Integer quantityReturned;

}
