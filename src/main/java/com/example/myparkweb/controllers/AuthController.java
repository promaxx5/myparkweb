package com.example.myparkweb.controllers;

import com.example.myparkweb.DTO.user.UserRegistrationDto;
import com.example.myparkweb.services.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@Controller
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        log.info("AuthController инициализирован");
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        log.debug("Отображение страницы входа");
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        log.debug("Отображение страницы регистрации");
        model.addAttribute("userDto", new UserRegistrationDto());
        return "registration";
    }
    @PostMapping("/registration")
    public String register(@ModelAttribute("userDto") UserRegistrationDto userDto, Model model) {
        log.debug("Обработка регистрации пользователя: {}", userDto.getEmail());
        try {
            authService.register(userDto);
            log.info("Пользователь успешно зарегистрирован: {}", userDto.getEmail());
            return "redirect:/login";
        } catch (Exception e) {
            log.warn("Ошибки валидации при регистрации: {}", e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "registration";
        }
    }
}