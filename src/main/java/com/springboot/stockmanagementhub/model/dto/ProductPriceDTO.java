package com.springboot.stockmanagementhub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductPriceDTO {
    List<ProductPrice> productPriceList;
    BigDecimal totalPrice;
}
