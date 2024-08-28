package com.springboot.stockmanagementhub.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductsToBePriced {
    @NotNull
    private Long productId;
    @Size(min = 1)
    private int quantity;
}
