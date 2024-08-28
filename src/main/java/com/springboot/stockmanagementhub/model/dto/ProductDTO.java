package com.springboot.stockmanagementhub.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class ProductDTO {
    @NotEmpty
    private String name;
    private Integer availableQuantity;
    private BigDecimal price;

    @Length(max = 20)
    private String colour;

    @Past
    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private String size;

    @NotNull(message = "product category id cannot be null")
    private Long productCategoryId;

    private final Map<String, String> settings = new HashMap<>();

}
