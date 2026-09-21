package com.maria.help_desk.handler;

import com.maria.help_desk.exception.ClosedFeatureException;
import com.maria.help_desk.exception.ResourceAlreadyExistsException;
import com.maria.help_desk.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> resourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClosedFeatureException.class)
    public ResponseEntity<ErrorMessage> closedFeatureException(ClosedFeatureException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.LOCKED.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.LOCKED);
    }

}
