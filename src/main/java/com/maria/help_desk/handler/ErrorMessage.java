package com.maria.help_desk.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

    private Integer statusCode;

    private String message;

    private String details;

    private LocalDateTime timestamp;

}
