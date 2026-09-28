package com.maria.help_desk.exception;

public class InvalidJsonException extends RuntimeException{

    public InvalidJsonException(String message){
        super(message);
    }
}
