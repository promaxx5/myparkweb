package com.example.myparkweb.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReviewDto implements Serializable {
    private String authorName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;


    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}