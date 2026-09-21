package com.maria.help_desk.dto;

import com.maria.help_desk.model.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO {

    @NotNull
    @Email(message = "Insert a valid email address.")
    private String email;

    @NotNull
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long.")
    private String password;

    private Role role;
}
