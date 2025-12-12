package com.example.myparkweb.controllers;

import com.example.myparkweb.DTO.booking.AddBookingDto;
import com.example.myparkweb.DTO.parking.ShowParkingDetailedInfoDto;
import com.example.myparkweb.services.BookingServiceImpl;
import com.example.myparkweb.services.CarServiceImpl;
import com.example.myparkweb.services.ParkingServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final ParkingServiceImpl parkingService;
    private final CarServiceImpl carService;

    public BookingController(BookingServiceImpl bookingService, ParkingServiceImpl parkingService, CarServiceImpl carService) {
        this.bookingService = bookingService;
        this.parkingService = parkingService;
        this.carService = carService;
        log.info("BookingController инициализирован");

    }


    @GetMapping("/create/{parkingId}")
    public String showBookingForm(@PathVariable String parkingId, Model model) {
        log.debug("Отображение формы бронирования");
        ShowParkingDetailedInfoDto parking = parkingService.findParkingById(parkingId);

        AddBookingDto bookingDTO = new AddBookingDto();
        bookingDTO.setParkingId(parkingId);

        model.addAttribute("parking", parking);
        model.addAttribute("bookingDTO", bookingDTO);


        model.addAttribute("myCars", carService.getMyCars());

        return "booking-create";
    }

    @PostMapping("/create")
    public String createBooking(@Valid @ModelAttribute("bookingDTO") AddBookingDto bookingDTO,
                                BindingResult bindingResult,
                                Model model) {

        log.debug("Обработка POST запроса на добавление бронирования");
        if (bindingResult.hasErrors()) {
            log.warn("Ошибка при добавление бронирования",bindingResult.getAllErrors());
            ShowParkingDetailedInfoDto parking = parkingService.findParkingById(bookingDTO.getParkingId());
            model.addAttribute("parking", parking);
            model.addAttribute("myCars", carService.getMyCars());
            return "booking-create";
        }

        bookingService.createBooking(bookingDTO);
        return "redirect:/parkings";
    }
}