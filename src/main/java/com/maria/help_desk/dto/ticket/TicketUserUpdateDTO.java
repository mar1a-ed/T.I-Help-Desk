package com.maria.help_desk.dto.ticket;

import com.maria.help_desk.model.Category;
import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketUserUpdateDTO {

    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters long.")
    private String title;

    @Size(min = 1, max = 400, message = "Description must be less than 400 characters long.")
    private String description;

    private Category category;

}
