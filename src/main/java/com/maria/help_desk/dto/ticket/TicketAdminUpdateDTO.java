package com.maria.help_desk.dto.ticket;

import com.maria.help_desk.model.Priority;
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
