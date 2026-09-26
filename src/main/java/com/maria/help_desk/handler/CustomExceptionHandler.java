package com.maria.help_desk.handler;

import com.maria.help_desk.exception.*;
import com.maria.help_desk.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorMessage> ticketNotFoundException(TicketNotFoundException e, WebRequest request){
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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> nullPointerException(NullPointerException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessage> invalidCredentialsException(InvalidCredentialsException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

}
