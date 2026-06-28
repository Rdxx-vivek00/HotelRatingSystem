package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    Rating createRating(Rating rating);

    List<Rating> getRatings();

    List<Rating> getRatingByUser(String userId);

    List<Rating> getRatingByHotel(String HotelId);


}
