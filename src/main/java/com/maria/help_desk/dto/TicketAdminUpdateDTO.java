package com.maria.help_desk.dto;

import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import lombok.*;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketAdminUpdateDTO {

    private Priority priority;

    @Email(message = "Insert a valid email address.")
    private String supportEmail;
}
