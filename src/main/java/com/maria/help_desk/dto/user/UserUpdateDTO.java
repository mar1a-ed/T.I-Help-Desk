package com.maria.help_desk.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

    @NotNull
    @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters long.")
    private String name;

    @Email(message = "Insert a valid email address.")
    private String email;

}
