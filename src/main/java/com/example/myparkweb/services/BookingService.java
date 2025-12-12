package com.example.myparkweb.services;

import com.example.myparkweb.DTO.booking.AddBookingDto;
import com.example.myparkweb.models.entities.Booking;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface BookingService {
    @Transactional
    void createBooking(AddBookingDto dto);

    @Transactional(readOnly = true)
    List<Booking> getMyTrips();

    @Transactional(readOnly = true)
    List<Booking> getIncomingBookings();

    @Transactional
    void cancelBooking(String bookingId) throws AccessDeniedException;
}
