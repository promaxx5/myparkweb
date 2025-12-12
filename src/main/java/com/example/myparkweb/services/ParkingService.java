package com.example.myparkweb.services;

import com.example.myparkweb.DTO.parking.AddParkingDto;
import com.example.myparkweb.DTO.parking.ShowParkingDetailedInfoDto;
import com.example.myparkweb.DTO.parking.ShowParkingInfoDto;
import com.example.myparkweb.models.entities.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParkingService {
    Page<ShowParkingInfoDto> findAllParkings(Pageable pageable);

    Page<ShowParkingInfoDto> findParkingsByAddress(String address, Pageable pageable);

    @Transactional(readOnly = true)
    ShowParkingDetailedInfoDto findParkingById(String id);

    @Transactional
    void addParking(AddParkingDto dto);

    @Transactional(readOnly = true)
    List<Parking> getMyParkings();

    @Transactional(readOnly = true)
    List<ShowParkingInfoDto> getTrendingParkings();

    @Transactional
    void deleteParking(String id);
}
