package com.maria.help_desk.service;

import com.maria.help_desk.dto.UserCreateDTO;
import com.maria.help_desk.dto.UserUpdateDTO;
import com.maria.help_desk.exception.ResourceAlreadyExistsException;
import com.maria.help_desk.exception.ResourceNotFoundException;
import com.maria.help_desk.model.Role;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User findUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        return user;
    }

    @Transactional
    public List<User> findAll(){
        List<User> users = userRepository.findAll();

        if(users == null){
            throw new ResourceNotFoundException("Users not found.");
        }

        return users;
    }

    @Transactional
    public User updateUser(Long id, UserUpdateDTO dto){
        User user = findUserById(id);

        if(user.getEmail().equals(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Enter an email address other than you current one.");
        }

        LocalDateTime updatedTime = LocalDateTime.now();

        user.setName(dto.getName());

        if(dto.getEmail() != null){
            user.setEmail(dto.getEmail());
        }

        user.setUpdatedAt(updatedTime);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void deleteUser(Long id){
        User user = findUserById(id);

        userRepository.delete(user);
    }

}







