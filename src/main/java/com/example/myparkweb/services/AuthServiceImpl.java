package com.example.myparkweb.services;

import com.example.myparkweb.DTO.user.UserRegistrationDto;
import com.example.myparkweb.models.entities.Role;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.models.enums.UserRoles;
import com.example.myparkweb.repositories.UserRoleRepository;
import com.example.myparkweb.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDto registrationDTO) {

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают!");
        }


        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }


        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setFullName(registrationDTO.getFullName());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setAge(registrationDTO.getAge());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));


        UserRoles roleEnum = UserRoles.valueOf(registrationDTO.getRole());

        Role userRole = roleRepository.findRoleByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.setRoles(Collections.singletonList(userRole));


        userRepository.save(user);
    }
}