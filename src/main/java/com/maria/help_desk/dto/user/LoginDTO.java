package com.maria.help_desk.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {

    @NotBlank
    @Email(message = "Insert a valid email address.")
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;
}
