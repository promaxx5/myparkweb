package com.example.myparkweb.repositories;

import com.example.myparkweb.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN FETCH b.parking " +
            "LEFT JOIN FETCH b.car " +
            "WHERE b.driver.id = :driverId")
    List<Booking> findByDriverId(@Param("driverId") String driverId);

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN FETCH b.parking p " +
            "LEFT JOIN FETCH b.driver " +
            "LEFT JOIN FETCH b.car " +
            "WHERE p.owner.id = :ownerId")
    List<Booking> findBookingsByParkingOwnerId(@Param("ownerId") String ownerId);


}