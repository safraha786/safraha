package com.example.safraha.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.safraha.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}

