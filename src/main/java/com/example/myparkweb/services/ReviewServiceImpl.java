package com.example.myparkweb.services;


import com.example.myparkweb.DTO.review.AddReviewDto;
import com.example.myparkweb.models.exceptions.EntityNotFoundException;
import com.example.myparkweb.models.entities.Parking;
import com.example.myparkweb.models.entities.Review;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.repositories.ParkingRepository;
import com.example.myparkweb.repositories.ReviewRepository;
import com.example.myparkweb.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j; // <--- Импорт для логгера
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserServiceImpl userService;
    private final ReviewRepository reviewRepository;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(UserServiceImpl userService, ReviewRepository reviewRepository, ParkingRepository parkingRepository, UserRepository userRepository) {
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        log.info("ReviewServiceImpl инициализирован");
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "parkingDetails", key = "#dto.parkingId"),
            @CacheEvict(value = "trendingParkings", allEntries = true)
    })
    public void addReview(AddReviewDto dto) {
        log.info("Запрос на добавление отзыва для парковки ID: {}", dto.getParkingId());

        Parking parking = parkingRepository.findById(dto.getParkingId())
                .orElseThrow(() -> {
                    log.warn("Не удалось добавить отзыв: Парковка с ID {} не найдена", dto.getParkingId());
                    return new EntityNotFoundException("Парковка не найдена");
                });

        User currentUser = userService.getCurrentUser();
        log.debug("Автор отзыва: {} (ID: {})", currentUser.getEmail(), currentUser.getId());

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setParking(parking);
        review.setAuthor(currentUser);

        reviewRepository.save(review);
        log.info("Отзыв успешно сохранен (ID парковки: {}, Оценка: {})", parking.getId(), dto.getRating());

        updateAverageRating(parking);
    }

    private void updateAverageRating(Parking parking) {
        log.debug("Пересчет среднего рейтинга для парковки ID: {}", parking.getId());

        Double avg = reviewRepository.findByParkingId(parking.getId()).stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);


        avg = Math.round(avg * 10.0) / 10.0;

        parking.setAverageRating(avg);
        parkingRepository.save(parking);

        log.info("Новый средний рейтинг парковки ID {}: {}", parking.getId(), avg);
    }
}