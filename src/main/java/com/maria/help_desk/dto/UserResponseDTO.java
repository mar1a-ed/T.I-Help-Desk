package com.maria.help_desk.dto;

import com.maria.help_desk.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id;

    private String email;

    private Role role;

    private LocalDateTime createdAt;
}
