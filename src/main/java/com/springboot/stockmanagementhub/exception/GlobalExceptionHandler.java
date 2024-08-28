package com.springboot.stockmanagementhub.exception;

import com.springboot.stockmanagementhub.model.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                "Validation failed due to constraint violations",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                                .map(error -> error.getDefaultMessage())
                                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                "Validation failed for method arguments",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid argument provided",
                "Invalid argument provided",
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        System.out.println("DataIntegrityViolationException caught in GlobalExceptionHandler");
        // Log the exception for debugging purposes
        ex.printStackTrace();

        // Extract relevant information from the exception
        String message = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Data integrity violation",
                "Data integrity violation occurred",
                List.of(message)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        // Create an ErrorResponse with detailed information
        ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                "An unexpected error occurred on the server.",
                List.of(ex.getMessage())
        );

        // Return the response entity with INTERNAL_SERVER_ERROR status
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

