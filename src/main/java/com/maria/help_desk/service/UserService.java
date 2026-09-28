package com.maria.help_desk.service;

import com.maria.help_desk.dto.user.*;
import com.maria.help_desk.exception.InvalidJsonException;
import com.maria.help_desk.exception.ResourceAlreadyExistsException;
import com.maria.help_desk.exception.UserNotFoundException;
import com.maria.help_desk.model.Role;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        if(dto == null){
            throw new InvalidJsonException("Invalid Json. Insert a valid information.");
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

    @Transactional
    public User getMyUser(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        return user;
    }

    @Transactional
    public User findUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found.")
        );

        return user;
    }

    @Transactional
    public List<User> findAll(){
        List<User> users = userRepository.findAll();

        if(users == null){
            throw new UserNotFoundException("Users not found.");
        }

        return users;
    }

    @Transactional
    public User updateMe(Authentication authentication, UserUpdateDTO dto){
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if(dto == null){
            throw new InvalidJsonException("Invalid Json. Insert a valid information.");
        }

        if(dto.getName() != null){
            user.setName(dto.getName());
        }

        if(dto.getEmail() != null){
            user.setEmail(dto.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUserByAdmin(Long id, UserUpdateAdminDTO dto){
        User user = findUserById(id);

        if(dto == null){
            throw new InvalidJsonException("Invalid Json. Insert a valid information.");
        }

        user.setRole(dto.getRole());

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void deleteUser(Long id){
        User user = findUserById(id);

        userRepository.delete(user);
    }

}







