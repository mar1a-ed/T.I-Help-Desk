package com.maria.help_desk.dto.user;

import com.maria.help_desk.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateAdminDTO {

    private Role role;
}
