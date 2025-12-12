package com.example.myparkweb.controllers;

import com.example.myparkweb.DTO.review.AddReviewDto;
import com.example.myparkweb.services.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        log.info("ReviewController инициализирован");
        this.reviewService = reviewService;
    }


    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute AddReviewDto reviewDTO,
                            BindingResult bindingResult) {

        log.debug("Обработка POST запроса на добавление отзыва");
        if (bindingResult.hasErrors()) {

            return "redirect:/parkings/" + reviewDTO.getParkingId();
        }

        try {
            reviewService.addReview(reviewDTO);
        } catch (Exception e) {
            log.warn("Ошибки валидации при добавлении парковки: {}", bindingResult.getAllErrors());
            e.printStackTrace();
        }


        return "redirect:/parkings/" + reviewDTO.getParkingId();
    }
}