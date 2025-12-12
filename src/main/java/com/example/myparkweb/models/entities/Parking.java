package com.example.myparkweb.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parkings")
public class Parking extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;


    @Column(name = "price_per_hour", columnDefinition = "DECIMAL(8,2)")
    private Double pricePerHour;

    @Column(name = "price_per_day", columnDefinition = "DECIMAL(8,2)")
    private Double pricePerDay;

    @Column(name = "count_places", nullable = false)
    private Integer countPlaces;


    @Column(name = "parking_type", nullable = false)
    private String parkingType;

    @Column(name = "other_type_description")
    private String otherTypeDescription;



    @Column(name = "is_for_disabled")
    private boolean isForDisabled;

    @Column(name = "has_security")
    private boolean hasSecurity;

    @Column(name = "has_lighting")
    private boolean hasLighting;

    @Column(name = "has_barrier")
    private boolean hasBarrier;

    @Column(name = "has_car_wash_nearby")
    private boolean hasCarWashNearby;

    @Column(name = "has_luggage_storage")
    private boolean hasLuggageStorage;

    @Column(name = "has_cctv")
    private boolean hasCctv;

    @Column(name = "has_24_7_access")
    private boolean has247Access;

    @Column(name = "has_ev_charger")
    private boolean hasEvCharger;

    @Column(name = "has_toilet")
    private boolean hasToilet;
    @Column(name = "average_rating")
    private Double averageRating = 0.0;


    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Review> reviews = new java.util.ArrayList<>();


    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();

    public Parking() {
    }



    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }

    public Double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(Double pricePerDay) { this.pricePerDay = pricePerDay; }

    public Integer getCountPlaces() { return countPlaces; }
    public void setCountPlaces(Integer countPlaces) { this.countPlaces = countPlaces; }

    public String getParkingType() { return parkingType; }
    public void setParkingType(String parkingType) { this.parkingType = parkingType; }

    public String getOtherTypeDescription() { return otherTypeDescription; }
    public void setOtherTypeDescription(String otherTypeDescription) { this.otherTypeDescription = otherTypeDescription; }



    public boolean isForDisabled() { return isForDisabled; }
    public void setForDisabled(boolean forDisabled) { isForDisabled = forDisabled; }

    public boolean isHasSecurity() { return hasSecurity; }
    public void setHasSecurity(boolean hasSecurity) { this.hasSecurity = hasSecurity; }

    public boolean isHasLighting() { return hasLighting; }
    public void setHasLighting(boolean hasLighting) { this.hasLighting = hasLighting; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public java.util.List<Review> getReviews() { return reviews; }
    public void setReviews(java.util.List<Review> reviews) { this.reviews = reviews; }
    public boolean isHasBarrier() { return hasBarrier; }
    public void setHasBarrier(boolean hasBarrier) { this.hasBarrier = hasBarrier; }

    public boolean isHasCarWashNearby() { return hasCarWashNearby; }
    public void setHasCarWashNearby(boolean hasCarWashNearby) { this.hasCarWashNearby = hasCarWashNearby; }

    public boolean isHasLuggageStorage() { return hasLuggageStorage; }
    public void setHasLuggageStorage(boolean hasLuggageStorage) { this.hasLuggageStorage = hasLuggageStorage; }

    public boolean isHasCctv() { return hasCctv; }
    public void setHasCctv(boolean hasCctv) { this.hasCctv = hasCctv; }

    public boolean isHas247Access() { return has247Access; }
    public void setHas247Access(boolean has247Access) { this.has247Access = has247Access; }

    public boolean isHasEvCharger() { return hasEvCharger; }
    public void setHasEvCharger(boolean hasEvCharger) { this.hasEvCharger = hasEvCharger; }

    public boolean isHasToilet() { return hasToilet; }
    public void setHasToilet(boolean hasToilet) { this.hasToilet = hasToilet; }

    public Set<Booking> getBookings() { return bookings; }
    public void setBookings(Set<Booking> bookings) { this.bookings = bookings; }
}