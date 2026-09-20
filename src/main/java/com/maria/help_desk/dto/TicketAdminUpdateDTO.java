package com.maria.help_desk.dto;

import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class TicketAdminUpdateDTO {

    private Priority priority;

    private Status status;

    @Email(message = "Insert a valid email address.")
    private String supportEmail;
}
