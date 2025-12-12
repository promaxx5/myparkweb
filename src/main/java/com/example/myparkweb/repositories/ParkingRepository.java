package com.example.myparkweb.repositories;

import com.example.myparkweb.models.entities.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {
    @Query("SELECT p FROM Parking p " +
            "LEFT JOIN p.bookings b " +
            "GROUP BY p " +
            "ORDER BY (p.averageRating + (COUNT(b) * 0.5)) DESC")
    List<Parking> findTrendingParkings(Pageable pageable);
    List<Parking> findByOwnerId(String ownerId);
    Page<Parking> findByAddressContainingIgnoreCase(String address, Pageable pageable);
}