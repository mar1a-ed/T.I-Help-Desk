package com.maria.help_desk.service;

import com.maria.help_desk.dto.user.LoginDTO;
import com.maria.help_desk.exception.InvalidCredentialsException;
import com.maria.help_desk.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void authenticateUserWithValidCredentialsStatus200(){
        String email = "mariaeduarda@gmail.com";
        String password = "12345678";
        String token = "jwt-token";

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(jwtUtils.getToken(authentication)).thenReturn(token);

        String result = authenticationService.getAuthentication(new LoginDTO(email, password));

        assertEquals(token, result);

    }

    @Test
    public void doNotAuthenticateUserWithInvalidCredentialsStatus401(){
        String email = "mariaeduarda@gmail.com";
        String password = "12345678910";

        when(authenticationManager.authenticate(any())).thenThrow(new InvalidCredentialsException("Invalid email or password."));

        assertThrows(InvalidCredentialsException.class, () -> authenticationService.getAuthentication(new LoginDTO(email, password)));
    }
}
