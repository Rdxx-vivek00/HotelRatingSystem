package com.lcwd.rating.services.impl;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.repository.RatingRepo;
import com.lcwd.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepo ratingRepo;
    @Override
    public Rating createRating(Rating rating) {
        String randomId= UUID.randomUUID().toString();
        rating.setRatingId(randomId);
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<Rating> getRatingByUser(String userId) {
        return ratingRepo.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotel(String hotelId) {
        return ratingRepo.findByHotelId(hotelId);
    }
}
