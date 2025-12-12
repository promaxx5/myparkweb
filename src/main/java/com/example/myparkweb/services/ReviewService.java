package com.example.myparkweb.services;

import com.example.myparkweb.DTO.review.AddReviewDto;
import com.example.myparkweb.models.entities.Parking;
import com.example.myparkweb.models.entities.Review;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    @Transactional
    void addReview(AddReviewDto dto);

}
