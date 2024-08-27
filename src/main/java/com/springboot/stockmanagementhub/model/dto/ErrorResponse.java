package com.springboot.stockmanagementhub.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> errors;

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
