package com.springboot.stockmanagementhub.model.dto;

import lombok.Data;

@Data
public class UpdateStockDTO {
    private long productId;
    private int quantityToBeAdded;

}
