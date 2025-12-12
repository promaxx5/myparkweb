package com.example.myparkweb.DTO.parking;

import com.example.myparkweb.DTO.ReviewDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowParkingDetailedInfoDto extends ShowParkingInfoDto implements Serializable {
    private String ownerId;
    private String description;
    private Integer countPlaces;
    private Double averageRating;
    private List<ReviewDto> reviews = new ArrayList<>();
    private boolean isForDisabled;

    private boolean hasSecurity;
    private boolean hasLighting;
    private boolean hasBarrier;
    private boolean hasCarWashNearby;
    private boolean hasCctv;
    private boolean has247Access;
    private boolean hasEvCharger;
    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCountPlaces() { return countPlaces; }
    public void setCountPlaces(Integer countPlaces) { this.countPlaces = countPlaces; }

    public boolean isForDisabled() { return isForDisabled; }
    public void setForDisabled(boolean forDisabled) { isForDisabled = forDisabled; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public boolean isHasSecurity() { return hasSecurity; }
    public void setHasSecurity(boolean hasSecurity) { this.hasSecurity = hasSecurity; }

    public boolean isHasLighting() { return hasLighting; }
    public void setHasLighting(boolean hasLighting) { this.hasLighting = hasLighting; }

    public boolean isHasBarrier() { return hasBarrier; }
    public void setHasBarrier(boolean hasBarrier) { this.hasBarrier = hasBarrier; }

    public boolean isHasCarWashNearby() { return hasCarWashNearby; }
    public void setHasCarWashNearby(boolean hasCarWashNearby) { this.hasCarWashNearby = hasCarWashNearby; }

    public boolean isHasCctv() { return hasCctv; }
    public void setHasCctv(boolean hasCctv) { this.hasCctv = hasCctv; }

    public boolean isHas247Access() { return has247Access; }
    public void setHas247Access(boolean has247Access) { this.has247Access = has247Access; }

    public boolean isHasEvCharger() { return hasEvCharger; }
    public void setHasEvCharger(boolean hasEvCharger) { this.hasEvCharger = hasEvCharger; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
}