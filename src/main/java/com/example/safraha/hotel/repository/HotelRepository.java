package com.example.safraha.hotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.safraha.hotel.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
