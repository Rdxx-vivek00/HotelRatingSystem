package com.lcwd.hotel.HotelService.services.impl;

import com.lcwd.hotel.HotelService.entities.Hotel;
import com.lcwd.hotel.HotelService.exception.ResourceNotFoundException;
import com.lcwd.hotel.HotelService.repositories.HotelRepo;
import com.lcwd.hotel.HotelService.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepo hotelRepo;
    @Override
    public Hotel create(Hotel hotel) {
        String randomUsedId= UUID.randomUUID().toString();
        hotel.setId(randomUsedId);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel get(String hotelId) {
        return hotelRepo.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("Hotel with given id not found:"+hotelId));
    }
}
