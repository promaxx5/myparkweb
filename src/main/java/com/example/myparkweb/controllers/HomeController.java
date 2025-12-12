package com.example.myparkweb.controllers;


import com.example.myparkweb.services.ParkingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class HomeController {
    private final ParkingServiceImpl parkingService;

    public HomeController(ParkingServiceImpl parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/")
    public String index(Model model) {
        log.debug("Отображение главной страницы");
        model.addAttribute("trendingParkings", parkingService.getTrendingParkings());
        return "index";
    }
}
