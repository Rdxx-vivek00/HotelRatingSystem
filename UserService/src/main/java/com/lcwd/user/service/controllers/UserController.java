package com.lcwd.user.service.controllers;

import com.lcwd.user.service.UserServiceApplication;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User createdUser= userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    int retryCount=1;
    //@CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService" , fallbackMethod = "ratingHotelFallback")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId)
    {
        log.info("retry count: {}",retryCount);
        retryCount++;
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }


    //creating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex)
    {

//      log.info("Fallback is executed because the service id down",ex.getMessage());
      User user=User.builder()
              .name("Dummy")
              .email("dummy@email.com")
              .about("This user is created because some service is down").build();
      return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users=userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
