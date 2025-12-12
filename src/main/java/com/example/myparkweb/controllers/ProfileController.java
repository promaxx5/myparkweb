package com.example.myparkweb.controllers;

import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserServiceImpl userService;
    private final CarServiceImpl carService;
    private final ParkingServiceImpl parkingService;
    private final BookingServiceImpl bookingService;

    public ProfileController(UserServiceImpl userService, CarServiceImpl carService, ParkingServiceImpl parkingService, BookingServiceImpl bookingService) {
        this.userService = userService;
        this.carService = carService;
        this.parkingService = parkingService;
        this.bookingService = bookingService;
        log.info("ProfileController инициализирован");
    }

    @GetMapping
    public String profilePage(Model model) {
        User user = userService.getCurrentUser();

        log.debug("Отображение профиля пользователя: {}", user.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("myCars", carService.getMyCars());
        model.addAttribute("myTrips", bookingService.getMyTrips());
        model.addAttribute("myParkings", parkingService.getMyParkings());
        model.addAttribute("incomingBookings", bookingService.getIncomingBookings());

        return "profile";
    }
}