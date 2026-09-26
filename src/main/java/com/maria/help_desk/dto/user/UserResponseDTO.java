package com.maria.help_desk.dto.user;

import com.maria.help_desk.model.Role;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {

    private Long id;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
