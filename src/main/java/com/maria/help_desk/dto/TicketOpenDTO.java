package com.maria.help_desk.dto;

import com.maria.help_desk.model.Category;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TicketOpenDTO {

    @NotNull
    @Email(message = "Insert a valid email address.")
    private String userEmail;

    @NotNull
    @Size(min = 1, max = 150, message = "Title must be between 1 and 150 characters long.")
    private String title;

    @Size(min = 1, max = 400, message = "Description must be less than 400 characters.")
    private String description;

    private Category category;
}
