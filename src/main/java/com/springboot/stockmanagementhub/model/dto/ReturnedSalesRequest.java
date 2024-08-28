package com.springboot.stockmanagementhub.model.dto;

import com.springboot.stockmanagementhub.model.enumeration.ReturnReason;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReturnedSalesRequest {
    private Long salesId;
    private List<ProductReturn> productReturn;
    private ReturnReason reasonForReturn;
    private String additionalComments;
    private LocalDate returnDate = LocalDate.now();

}
