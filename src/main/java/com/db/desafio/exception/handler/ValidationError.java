package com.db.desafio.exception.handler;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ValidationError {

    private String field;
    private String message;
    public ValidationError(FieldError fieldError) {
        this.field = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
    }
}
