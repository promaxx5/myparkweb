package com.example.myparkweb.services;

import com.example.myparkweb.DTO.car.AddCarDto;
import com.example.myparkweb.models.entities.Car;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarService {
    @Transactional(readOnly = true)
    List<Car> getMyCars();

    @Transactional
    void addCar(AddCarDto dto);
}
