package com.springboot.stockmanagementhub.model.dto;

import lombok.Data;


import java.util.List;


@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String description;
    private List<String> errors;

    public ErrorResponse(int status, String message, String description, List<String> errors) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.errors = errors;
    }

    // Static method to build ErrorResponse for exceptions
    public static ErrorResponse buildErrorResponse(int status, String message, String description, List<String> errors) {
        return new ErrorResponse(status, message, description, errors);
    }
}
