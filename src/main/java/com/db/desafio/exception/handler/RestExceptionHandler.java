package com.db.desafio.exception.handler;

import com.db.desafio.exception.BadRequestException;
import com.db.desafio.exception.ForbiddenActionException;
import com.db.desafio.exception.NoContentException;
import com.db.desafio.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Exception> handlerResourceNotFoundException(ResourceNotFoundException e) {
        return newApiExceptionResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Exception> handlerBadRequestException(BadRequestException e) {
        return newApiExceptionResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<Exception> handlerForbiddenActionException(ForbiddenActionException e) {
        return newApiExceptionResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Exception> handlerNoContentException(NoContentException e) {
        return newApiExceptionResponseEntity(HttpStatus.NO_CONTENT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Exception> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        return new ResponseEntity<>(
                Exception.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Erro de validação")
                        .timestamp(LocalDateTime.now())
                        .errors(fieldErrors.stream().map(ValidationError::new).toList())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Exception> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return newApiExceptionResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private static ResponseEntity<Exception> newApiExceptionResponseEntity(HttpStatus status, String message) {
        return new ResponseEntity<>(
                Exception.builder()
                        .status(status.value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build(),
                status
        );
    }

}
