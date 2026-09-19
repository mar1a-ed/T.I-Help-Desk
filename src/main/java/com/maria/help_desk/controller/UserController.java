package com.maria.help_desk.controller;

import com.maria.help_desk.dto.UserCreateDTO;
import com.maria.help_desk.dto.UserMapper;
import com.maria.help_desk.dto.UserResponseDTO;
import com.maria.help_desk.model.User;
import com.maria.help_desk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto){
        User user = userService.createUser(dto);

        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }
}
