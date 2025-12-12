package com.example.myparkweb.services;

import com.example.myparkweb.DTO.car.AddCarDto;
import com.example.myparkweb.models.entities.Car;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.repositories.CarRepository;
import com.example.myparkweb.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private final UserServiceImpl userService;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CarServiceImpl(UserServiceImpl userService, CarRepository carRepository, UserRepository userRepository, ModelMapper mapper) {
        this.userService = userService;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        log.info("CarServiceImpl инициализирован");
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userCars", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<Car> getMyCars() {
        log.debug("Получение списка всех машин пользователя");
        User currentUser = userService.getCurrentUser();
        return carRepository.findByOwnerId(currentUser.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCars", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public void addCar(AddCarDto dto) {
        log.debug("Добавление машины", dto);
        Car car = mapper.map(dto, Car.class);
        User owner = userService.getCurrentUser();
        car.setOwner(owner);
        carRepository.save(car);
    }
}