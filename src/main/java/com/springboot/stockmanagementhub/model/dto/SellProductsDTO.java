package com.springboot.stockmanagementhub.model.dto;

import com.springboot.stockmanagementhub.model.CustomerInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SellProductsDTO {
    @NotNull
    private List<ProductsToBePriced> productsToBeSold;
    private CustomerInfo customerInfo;
}
