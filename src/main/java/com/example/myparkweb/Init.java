package com.example.myparkweb;

import com.example.myparkweb.models.entities.Role;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.models.enums.UserRoles;
import com.example.myparkweb.repositories.UserRoleRepository;
import com.example.myparkweb.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Init(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void initRoles() {
        if (roleRepository.count() < 5) {

            roleRepository.saveAll(List.of(
                    new Role(UserRoles.ADMIN),
                    new Role(UserRoles.MODERATOR),
                    new Role(UserRoles.BOTH),
                    new Role(UserRoles.DRIVER),
                    new Role(UserRoles.OWNER)
            ));

        } else {

        }
    }

    @Override
    public void run(String... args) {
        initRoles();
        if (userRepository.findByEmail("moderator@park.com").isEmpty()) {
            User moderator = new User();
            moderator.setPhoneNumber("+79999999999");
            moderator.setEmail("moderator@park.com");
            moderator.setFullName("Главный Модератор");
            moderator.setAge(30);
            moderator.setPassword(passwordEncoder.encode("admin123"));



            Role modRole = roleRepository.findRoleByName(UserRoles.MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));

            moderator.setRoles(Collections.singletonList(modRole));

            userRepository.save(moderator);
            System.out.println("МОДЕРАТОР СОЗДАН: login: moderator@park.com, pass: admin123");
        }
    }
}