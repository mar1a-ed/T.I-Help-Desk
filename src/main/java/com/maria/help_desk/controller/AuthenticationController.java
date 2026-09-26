package com.maria.help_desk.controller;

import com.maria.help_desk.dto.user.LoginDTO;
import com.maria.help_desk.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> getAuthentication(@RequestBody @Valid LoginDTO dto){
        return ResponseEntity.ok().body("Token: "+authenticationService.getAuthentication(dto));
    }
}
