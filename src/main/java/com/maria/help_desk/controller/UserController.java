package com.maria.help_desk.controller;

import com.maria.help_desk.dto.user.UserCreateDTO;
import com.maria.help_desk.dto.user.UserMapper;
import com.maria.help_desk.dto.user.UserResponseDTO;
import com.maria.help_desk.dto.user.UserUpdateDTO;
import com.maria.help_desk.model.User;
import com.maria.help_desk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPPORT')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto){
        User user = userService.createUser(dto);

        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPPORT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable(value = "id") Long id){
        User user = userService.findUserById(id);

        UserResponseDTO dto = UserMapper.toDto(user);
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserUpdateDTO dto){
        User user = userService.updateUser(id, dto);

        UserResponseDTO userDto = UserMapper.toDto(user);

        userDto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

}
