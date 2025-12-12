package com.example.myparkweb.controllers;


import com.example.myparkweb.DTO.parking.AddParkingDto;
import com.example.myparkweb.DTO.parking.ShowParkingDetailedInfoDto;
import com.example.myparkweb.DTO.parking.ShowParkingInfoDto;
import com.example.myparkweb.services.ParkingServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequestMapping("/parkings")
public class ParkController {

    private final ParkingServiceImpl parkingService;

    public ParkController(ParkingServiceImpl parkingService) {
        log.info("ParkController инициализирован");
        this.parkingService = parkingService;
    }


    @GetMapping
    public String listParkings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(required = false) String search,
            Model model) {

        log.debug("Отображение списка парковок: страница={}, размер={}, поиск={}",
                page, size, search);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ShowParkingInfoDto> parkingPage;

        if (search != null && !search.isBlank()) {
            parkingPage = parkingService.findParkingsByAddress(search, pageable);
            model.addAttribute("search", search);
        } else {
            parkingPage = parkingService.findAllParkings(pageable);
        }

        model.addAttribute("parkings", parkingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", parkingPage.getTotalPages());

        return "parking-list";
    }


    @GetMapping("/{id}")
    public String parkingDetails(@PathVariable String id, Model model) {
        log.debug("Запрос деталей парковки: {}", id);
        ShowParkingDetailedInfoDto parkingDetails = parkingService.findParkingById(id);
        model.addAttribute("parking", parkingDetails);
        return "parking-details";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.debug("Отображение формы добавления парковки");
        model.addAttribute("addParkingDTO", new AddParkingDto());
        return "parking-add";
    }


    @PostMapping("/add")
    public String addParking(
            @Valid @ModelAttribute("addParkingDTO") AddParkingDto addParkingDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        log.debug("Обработка POST запроса на добавление парковки");
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при добавлении парковки: {}", bindingResult.getAllErrors());
            return "parking-add";
        }

        parkingService.addParking(addParkingDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Парковка успешно добавлена!");

        return "redirect:/parkings";
    }

    @GetMapping("/{id}/delete")
    public String deleteParking(@PathVariable String id, RedirectAttributes redirectAttributes) {
        log.debug("Запрос на удаление компании: {}", id);
        parkingService.deleteParking(id);
        redirectAttributes.addFlashAttribute("successMessage", "Парковка удалена.");
        return "redirect:/parkings";
    }
}