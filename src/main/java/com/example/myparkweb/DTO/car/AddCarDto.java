package com.example.myparkweb.DTO.car;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public class AddCarDto implements Serializable {

    @NotEmpty(message = "Госномер обязателен")
    @Pattern(regexp = "^[А-Яа-я0-9]{6,9}$", message = "Некорректный формат номера")
    private String licensePlate;

    @NotEmpty(message = "Марка автомобиля обязательна")
    private String make;

    @NotEmpty(message = "Модель автомобиля обязательна")
    private String model;


    public AddCarDto() {}

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}