package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        String randomUserId= UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user= userRepository
                .findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("resource not found with the given Id"+userId));

//          fetch rating of the above user from rating service
//    localhost:8083/ratings/users/e16fcece-ae42-4907-a3e6-13a458566a57

         Rating[] ratingsOfUser=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        log.info("{} ",ratingsOfUser);
        List<Rating> ratings= Arrays.stream(ratingsOfUser).toList();

       List<Rating> ratingList= ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
           //http://localhost:8082/hotels/2f654521-92ae-4163-b419-94434ceed06a
        ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
        Hotel hotel= forEntity.getBody();
        log.info("response status code :",forEntity.getStatusCode());
            //set the hotel to rating

           rating.setHotel(hotel);
            //return the rating

               return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);

        return user;
    }
}
