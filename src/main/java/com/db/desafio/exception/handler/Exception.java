package com.db.desafio.exception.handler;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class Exception {

    private int status;
    private String message;
    private List<ValidationError> errors;
    private LocalDateTime timestamp;

}
