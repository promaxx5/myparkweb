package com.example.myparkweb.services;

import com.example.myparkweb.DTO.booking.AddBookingDto;
import com.example.myparkweb.models.exceptions.EntityNotFoundException;
import com.example.myparkweb.models.entities.Booking;
import com.example.myparkweb.models.entities.Car;
import com.example.myparkweb.models.entities.Parking;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.models.enums.UserRoles;
import com.example.myparkweb.repositories.BookingRepository;
import com.example.myparkweb.repositories.CarRepository;
import com.example.myparkweb.repositories.ParkingRepository;
import com.example.myparkweb.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final UserServiceImpl userService;
    private final BookingRepository bookingRepository;
    private final ParkingRepository parkingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(UserServiceImpl userService, BookingRepository bookingRepository,
                              ParkingRepository parkingRepository,
                              CarRepository carRepository,
                              UserRepository userRepository) {
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.parkingRepository = parkingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        log.info("BookingServiceImpl инициализирован");
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userTrips", allEntries = true),
            @CacheEvict(value = "ownerBookings", allEntries = true)
    })
    public void createBooking(AddBookingDto dto) {
        log.debug("Добавление нового бронирования: {}");
        Parking parking = parkingRepository.findById(dto.getParkingId())
                .orElseThrow(() -> new EntityNotFoundException("Парковка не найдена"));

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Машина не найдена"));

        User driver = userService.getCurrentUser();

        long hours = ChronoUnit.HOURS.between(dto.getStartTime(), dto.getEndTime());
        if (hours < 1) hours = 1;
        Double totalCost = hours * parking.getPricePerHour();

        Booking booking = new Booking();
        booking.setParking(parking);
        booking.setCar(car);
        booking.setDriver(driver);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setTotalCost(totalCost);
        booking.setStatus("ACTIVE");

        bookingRepository.save(booking);
        log.info("Бронирование успешно добавлена: {} на парковке {}", booking.getCar(), booking.getParking());

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userBooking", key = "#root.methodName + '_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<Booking> getMyTrips() {
        log.debug("Получение списка всех бронирований пользователя");
        User user = userService.getCurrentUser();
        return bookingRepository.findByDriverId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ownerBookings", key = "#root.methodName + '_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<Booking> getIncomingBookings() {
        log.debug("Получение списка всех запросов на  бронирование владельца парковки ");
        User user = userService.getCurrentUser();
        return bookingRepository.findBookingsByParkingOwnerId(user.getId());
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userTrips", allEntries = true),
            @CacheEvict(value = "ownerBookings", allEntries = true)
    })
    public void cancelBooking(String bookingId) throws AccessDeniedException {
        log.debug("Отмена бронирование");
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Бронирование не найдено"));

        User currentUser = userService.getCurrentUser();

        boolean isDriver = booking.getDriver().getId().equals(currentUser.getId());
        boolean isParkingOwner = booking.getParking().getOwner().getId().equals(currentUser.getId());

        boolean isModerator = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == UserRoles.MODERATOR || role.getName() == UserRoles.ADMIN);

        if (isDriver || isParkingOwner || isModerator) {
            log.info("Бронирование отменено");
            bookingRepository.delete(booking);
        } else {
            log.info("Бронирование не отменено");
            throw new AccessDeniedException("Вы не можете отменить это бронирование");
        }
    }
}