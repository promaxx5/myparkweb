package com.example.myparkweb.services;

import com.example.myparkweb.DTO.user.UserRegistrationDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    void register(UserRegistrationDto registrationDTO);
}
