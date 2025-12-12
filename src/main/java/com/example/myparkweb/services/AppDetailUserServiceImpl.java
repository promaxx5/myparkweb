package com.example.myparkweb.services;



import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.repositories.UserRepository;

import com.example.myparkweb.security.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class AppDetailUserServiceImpl implements AppDetailUserService {

    private final UserRepository userRepository;

    public AppDetailUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + email + " не найден!"));


        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toList());


        return new CustomUserDetails(user);
    }
}