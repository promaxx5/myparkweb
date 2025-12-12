package com.example.myparkweb.controllers;

import com.example.myparkweb.DTO.car.AddCarDto;
import com.example.myparkweb.services.CarServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarServiceImpl carService;

    public CarController(CarServiceImpl carService) {
        log.info("CarController инициализирован");
        this.carService = carService;
    }


    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("addCarDTO", new AddCarDto());
        return "car-add";
    }


    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute("addCarDTO") AddCarDto addCarDTO,
                         BindingResult bindingResult) {
        log.debug("Обработка POST запроса на добавление машины");
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при добавлении машины: {}", bindingResult.getAllErrors());
            return "car-add";
        }

        carService.addCar(addCarDTO);
        return "redirect:/";
    }
}