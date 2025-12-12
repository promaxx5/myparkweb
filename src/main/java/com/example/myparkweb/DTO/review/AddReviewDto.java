package com.example.myparkweb.DTO.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AddReviewDto implements Serializable {

    @NotNull
    private String parkingId;

    @NotNull(message = "Поставьте оценку")
    @Min(1) @Max(5)
    private Integer rating;

    @NotEmpty(message = "Напишите комментарий")
    private String comment;


    public String getParkingId() { return parkingId; }
    public void setParkingId(String parkingId) { this.parkingId = parkingId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}