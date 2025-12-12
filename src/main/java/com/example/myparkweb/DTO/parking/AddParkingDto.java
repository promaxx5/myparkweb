package com.example.myparkweb.DTO.parking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AddParkingDto implements Serializable {

    @NotEmpty(message = "Название объекта обязательно")
    private String name;

    @NotEmpty(message = "Адрес парковки обязателен")
    private String address;

    @NotNull(message = "Укажите количество мест")
    @Min(value = 1, message = "Должно быть хотя бы одно место")
    private Integer countPlaces;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Double pricePerHour;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Double pricePerDay;

    @NotEmpty(message = "Выберите тип места")
    private String parkingType;

    private String otherTypeDescription;


    private boolean isForDisabled;
    private boolean hasSecurity;
    private boolean hasLighting;
    private boolean hasBarrier;
    private boolean hasCarWashNearby;
    private boolean hasLuggageStorage;
    private boolean hasCctv;
    private boolean has247Access;
    private boolean hasEvCharger;
    private boolean hasToilet;

    // Геттеры и Сеттеры
    public AddParkingDto() {}

    // Основные поля
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getCountPlaces() { return countPlaces; }
    public void setCountPlaces(Integer countPlaces) { this.countPlaces = countPlaces; }

    public Double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(Double pricePerHour) { this.pricePerHour = pricePerHour; }

    public Double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(Double pricePerDay) { this.pricePerDay = pricePerDay; }

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
}