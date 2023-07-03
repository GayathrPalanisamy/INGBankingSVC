package com.ing.banking.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Set;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> exception(CustomerNotFoundException exception) {
        return new ResponseEntity<>("Customer Id not found ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LoanCreationException.class)
    public ResponseEntity<Object> exception(LoanCreationException exception) {
        return new ResponseEntity<>("Error while creating Customer", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<StringBuilder> exception(ConstraintViolationException exception,
                                                   ServletWebRequest webRequest) throws IOException {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : violations) {
            sb.append(constraintViolation.getMessage());
            sb.append(System.getProperty("line.separator"));
        }
        return ResponseEntity.badRequest().body(sb);
    }
}