package com.db.desafio.exception.handler;

import com.db.desafio.exception.BadRequestException;
import com.db.desafio.exception.ForbiddenActionException;
import com.db.desafio.exception.NoContentException;
import com.db.desafio.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
        String constraintName = ((ConstraintViolationException)e.getCause()).getConstraintName();
        List<ValidationError> errors =  new ArrayList<ValidationError>();
        FieldError field = new FieldError("","DataIntegrityViolation", constraintName);
        ValidationError obj = new ValidationError(field);
        errors.add(obj);
        return new ResponseEntity<>(
                Exception.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Erro de validação")
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        String mensagemErro = "Erro ao fazer o parse da data. Formato inválido.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
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
