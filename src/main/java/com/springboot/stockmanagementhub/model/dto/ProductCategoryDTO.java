package com.springboot.stockmanagementhub.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class ProductCategoryDTO {
    @NotEmpty(message = "Category name cannot be null")
    @Length(max = 20)
    private String categoryName;

    @Past
    private LocalDate createdDate;

    @NotNull(message = "Location id cannot be null")
    private Long locationId;
}
