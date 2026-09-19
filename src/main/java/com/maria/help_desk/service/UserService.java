package com.maria.help_desk.service;

import com.maria.help_desk.dto.UserCreateDTO;
import com.maria.help_desk.exception.ResourceAlreadyExistsException;
import com.maria.help_desk.model.Role;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreateDTO dto){
        User user = new User();

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email already exists! Insert another valid email.");
        }

        String role;

        if(dto.getRole() == null){
            role = "ROLE_USER";
        }else{
            role = dto.getRole().toString();
        }

        String password = dto.getPassword();
        LocalDateTime createdTime = LocalDateTime.now();

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role));
        user.setCreatedAt(createdTime);

        userRepository.save(user);

        return user;
    }
}
