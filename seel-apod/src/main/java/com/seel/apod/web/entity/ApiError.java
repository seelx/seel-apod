package com.seel.apod.web.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    public void setStatus(HttpStatus status) {this.status = status;}
    public HttpStatus getStatus() {return status;}

    private String message;
    public void setMessage(String message) {this.message = message;}
    public String getMessage() {return message;}

    private List<String> errors;
    public void setErrors(List<String> errors) {this.errors = errors; }
    public List<String> getErrors() {return errors; }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}