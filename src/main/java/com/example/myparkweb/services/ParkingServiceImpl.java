package com.example.myparkweb.services;

import com.example.myparkweb.DTO.ReviewDto;
import com.example.myparkweb.DTO.parking.AddParkingDto;
import com.example.myparkweb.DTO.parking.ShowParkingDetailedInfoDto;
import com.example.myparkweb.DTO.parking.ShowParkingInfoDto;
import com.example.myparkweb.models.exceptions.EntityNotFoundException;
import com.example.myparkweb.models.entities.Parking;
import com.example.myparkweb.models.entities.User;
import com.example.myparkweb.repositories.ParkingRepository;
import com.example.myparkweb.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j; // <--- 1. Добавляем Lombok для логов
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import com.example.myparkweb.models.enums.UserRoles;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParkingServiceImpl implements ParkingService {

    private final UserServiceImpl userService;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public ParkingServiceImpl(UserServiceImpl userService, ParkingRepository parkingRepository,
                              UserRepository userRepository,
                              ModelMapper mapper) {
        this.userService = userService;
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        log.info("ParkingServiceImpl инициализирован");
    }

    @Override
    public Page<ShowParkingInfoDto> findAllParkings(Pageable pageable) {
        log.debug("Запрос списка всех парковок. Страница: {}", pageable.getPageNumber());
        return parkingRepository.findAll(pageable)
                .map(parking -> mapper.map(parking, ShowParkingInfoDto.class));
    }

    @Override
    public Page<ShowParkingInfoDto> findParkingsByAddress(String address, Pageable pageable) {
        log.debug("Поиск парковок по адресу: '{}'", address); // <--- Используем {} для вставки значений
        return parkingRepository.findByAddressContainingIgnoreCase(address, pageable)
                .map(parking -> mapper.map(parking, ShowParkingInfoDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "parkingDetails", key = "#id")
    public ShowParkingDetailedInfoDto findParkingById(String id) {
        log.debug("Запрос детальной информации о парковке ID: {}", id); // <--- Исправлен синтаксис
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Попытка запроса несуществующей парковки ID: {}", id); // Логируем Warning при ошибке
                    return new EntityNotFoundException("Парковка с ID " + id + " не найдена");
                });

        ShowParkingDetailedInfoDto dto = mapper.map(parking, ShowParkingDetailedInfoDto.class);

        if (parking.getOwner() != null) {
            dto.setOwnerId(parking.getOwner().getId());
        }

        List<ReviewDto> reviewDTOs = parking.getReviews().stream()
                .map(review -> {
                    ReviewDto rDto = new ReviewDto();
                    rDto.setRating(review.getRating());
                    rDto.setComment(review.getComment());
                    rDto.setCreatedAt(review.getCreatedAt());
                    if (review.getAuthor() != null) {
                        rDto.setAuthorName(review.getAuthor().getFullName());
                    } else {
                        rDto.setAuthorName("Аноним");
                    }
                    return rDto;
                })
                .collect(Collectors.toList());

        dto.setReviews(reviewDTOs);
        return dto;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userParkings", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"),
            @CacheEvict(value = "trendingParkings", allEntries = true)
    })
    public void addParking(AddParkingDto dto) {
        User owner = userService.getCurrentUser();
        // Используем INFO для важных действий (создание)
        log.info("Пользователь {} добавляет новую парковку по адресу: {}", owner.getEmail(), dto.getAddress());

        Parking parking = mapper.map(dto, Parking.class);
        parking.setOwner(owner);

        Parking savedParking = parkingRepository.save(parking);
        log.debug("Парковка успешно сохранена с ID: {}", savedParking.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userParkings", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<Parking> getMyParkings() {
        User user = userService.getCurrentUser();
        log.debug("Запрос 'Мои парковки' для пользователя: {}", user.getEmail());
        return parkingRepository.findByOwnerId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "trendingParkings", key = "'top2'")
    public List<ShowParkingInfoDto> getTrendingParkings() {
        log.debug("Запрос списка трендовых парковок (Top 2)");
        return parkingRepository.findTrendingParkings(PageRequest.of(0, 2))
                .stream()
                .map(parking -> mapper.map(parking, ShowParkingInfoDto.class))
                .toList();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "parkingDetails", key = "#id"),
            @CacheEvict(value = "trendingParkings", allEntries = true),
            @CacheEvict(value = "userParkings", allEntries = true)
    })
    public void deleteParking(String id) {
        User currentUser = userService.getCurrentUser();
        log.info("Попытка удаления парковки ID: {} пользователем {}", id, currentUser.getEmail());

        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Парковка не найдена"));

        boolean isOwner = parking.getOwner().getId().equals(currentUser.getId());


        boolean isModerator = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName() == UserRoles.MODERATOR || role.getName() == UserRoles.ADMIN);

        if (isOwner || isModerator) {
            parkingRepository.delete(parking);
            log.info("Парковка ID: {} успешно удалена", id);
        } else {
            log.warn("Отказано в доступе на удаление парковки ID: {} для пользователя {}", id, currentUser.getEmail());
            throw new AccessDeniedException("У вас нет прав удалять эту парковку!");
        }
    }
}