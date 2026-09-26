package com.maria.help_desk.dto.user;

import com.maria.help_desk.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public static List<UserResponseDTO> toDtos(List<User> users){
        List<UserResponseDTO> dto = new ArrayList<>();

        for(User user : users){
            dto.add(toDto(user));
        }

        return dto;
    }
}
