package com.maria.help_desk.controller;

import com.maria.help_desk.dto.UserCreateDTO;
import com.maria.help_desk.dto.UserMapper;
import com.maria.help_desk.dto.UserResponseDTO;
import com.maria.help_desk.dto.UserUpdateDTO;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.UserRepository;
import com.maria.help_desk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable(value = "id") Long id){
        User user = userService.findUserById(id);

        UserResponseDTO dto = UserMapper.toDto(user);
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<User> users = userService.findAll();

        List<UserResponseDTO> dto = UserMapper.toDtos(users);

        for(UserResponseDTO user : dto){
            Long id = user.getId();
            user.add(linkTo(methodOn(UserController.class).findUserById(id)).withSelfRel());
        }

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserUpdateDTO dto){
        User user = userService.updateUser(id, dto);

        UserResponseDTO userDto = UserMapper.toDto(user);

        userDto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

}
