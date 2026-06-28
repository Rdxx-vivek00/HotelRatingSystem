package com.lcwd.user.service.controllers;

import com.lcwd.user.service.UserServiceApplication;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId)
    {
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users=userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
