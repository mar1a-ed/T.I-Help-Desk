package com.maria.help_desk.dto;

import com.maria.help_desk.model.Role;
import com.maria.help_desk.model.User;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {

    private Long id;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
