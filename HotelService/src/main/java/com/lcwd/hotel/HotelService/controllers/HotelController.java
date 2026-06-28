package com.lcwd.hotel.HotelService.controllers;

import com.lcwd.hotel.HotelService.entities.Hotel;
import com.lcwd.hotel.HotelService.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel)
    {
        return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId)
    {
        return new ResponseEntity<>(hotelService.get(hotelId),HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels()
    {
        return new ResponseEntity<>(hotelService.getAllHotels(),HttpStatus.OK);
    }
}
