package com.lcwd.rating.controller;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating)
    {
        return new ResponseEntity<>(ratingService.createRating(rating), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings()
    {
        return new ResponseEntity<>(ratingService.getRatings(),HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getAllRatingsByUser(@PathVariable String userId)
    {
        return new ResponseEntity<>(ratingService.getRatingByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getAllRatingsByHotel(@PathVariable String hotelId)
    {
        return new ResponseEntity<>(ratingService.getRatingByHotel(hotelId),HttpStatus.OK);
    }
}
