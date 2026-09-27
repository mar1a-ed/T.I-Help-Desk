package com.maria.help_desk.service;

import com.maria.help_desk.dto.user.UserCreateDTO;
import com.maria.help_desk.exception.ResourceAlreadyExistsException;
import com.maria.help_desk.exception.UserNotFoundException;
import com.maria.help_desk.model.Role;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserWithValidCredentials(){
        String email = "lolita@gmail.com";
        String password = "12345678";
        String role = "ROLE_ADMIN";

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role));
        user.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(user)).thenReturn(user);

        User userResult = userService.createUser(new UserCreateDTO(email, password, Role.valueOf(role)));

        assertNotNull(userResult);
        verify(userRepository).save(user);

    }

    @Test
    public void createUserWithInvalidEmail(){
        String email = "mariaeduarda@gmail.com";
        String password = "12345678";

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(user)).thenThrow(new ResourceAlreadyExistsException("Email already exists."));

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(new UserCreateDTO(email, password, Role.ROLE_USER)));
    }

    @Test
    public void findByValidId(){
        Long id = 3L;

        User user = new User();
        user.setId(id);
        user.setEmail("lilian@gmail.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User userResult = userService.findUserById(id);

        assertNotNull(userResult);
        assertEquals(user.getEmail(), userResult.getEmail());
    }

    @Test
    public void findByInvalidId(){
        Long id = 77L;

        when(userRepository.findById(id)).thenThrow(new UserNotFoundException("User not found."));

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(id));
    }
}
