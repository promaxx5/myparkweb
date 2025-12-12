package com.example.myparkweb.models.entities;



import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    public Review() {
    }


    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public Parking getParking() { return parking; }
    public void setParking(Parking parking) { this.parking = parking; }
}