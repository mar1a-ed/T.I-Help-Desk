package com.maria.help_desk.controller;

import com.maria.help_desk.dto.user.*;
import com.maria.help_desk.handler.ErrorMessage;
import com.maria.help_desk.model.User;
import com.maria.help_desk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "User", description = "User resources.")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a user", description = "Create a user.", responses = {
            @ApiResponse(responseCode = "201", description = "Successful request and return of the user's public data.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "The request was unsuccessful because the json is null or invalid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "409", description = "The request was unsuccessful because the user email already exists.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto){
        User user = userService.createUser(dto);
        UserResponseDTO userDto = UserMapper.toDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @Operation(summary = "Get your user", description = "Get your profile data", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and the return of the user's data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "The resource was unsuccessful because the user was not found or does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyUser(Authentication authentication) {
        User user = userService.getMyUser(authentication);
        UserResponseDTO userDto = UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Find a user by id", description = "Find a user by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the user's public data, and a hypermedia with the link of the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user is not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user is not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the user was not found or does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable(value = "id") Long id){
        User user = userService.findUserById(id);

        UserResponseDTO dto = UserMapper.toDto(user);
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Find all users", description = "Find all users.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of all the user's public data, and a hypermedia with the link of the each resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user is not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user is not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the users were not found or do not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
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

    @Operation(summary = "Update my user", description = "Update my user profile.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of all the user's public data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user is not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user is not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the user was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PutMapping("/update/me")
    public ResponseEntity<UserResponseDTO> updateMe(Authentication authentication, UserUpdateDTO dto){
        User user = userService.updateMe(authentication, dto);
        UserResponseDTO userDto = UserMapper.toDto(user);
        return ResponseEntity.ok().body(userDto);
    }

    @Operation(summary = "Update a user", description = "Update a user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of all the user's public data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user is not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user is not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the user was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserUpdateAdminDTO dto){
        User user = userService.updateUserByAdmin(id, dto);

        UserResponseDTO userDto = UserMapper.toDto(user);

        userDto.add(linkTo(methodOn(UserController.class).findAll()).withRel("List of users."));

        return ResponseEntity.ok().body(userDto);
    }

    @Operation(summary = "Delete a user", description = "Delete a user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request."),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user is not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user is not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the user was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

}
