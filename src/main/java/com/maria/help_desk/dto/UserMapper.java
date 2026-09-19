package com.maria.help_desk.dto;

import com.maria.help_desk.model.User;

public class UserMapper {

    public static UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
