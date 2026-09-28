package com.maria.help_desk.controller;

import com.maria.help_desk.dto.user.LoginDTO;
import com.maria.help_desk.handler.ErrorMessage;
import com.maria.help_desk.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authentication", description = "Resources for authenticating with the API.")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Authenticate with the API.", description = "Resource for authenticate with the API.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of a Bearer Token.",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<String> getAuthentication(@RequestBody @Valid LoginDTO dto){
        return ResponseEntity.ok().body(authenticationService.getAuthentication(dto));
    }
}
