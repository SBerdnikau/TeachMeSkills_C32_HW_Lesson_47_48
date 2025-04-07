package com.tms.controller;

import com.tms.model.User;
import com.tms.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user)  {
        logger.info("Received request to create user: {}", user.getFirstname());
        Boolean result = userService.createUser(user);
        if (!result) {
            logger.error("Failed to create user: {}", user.getFirstname());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User created successfully: {}", user.getFirstname());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") @Parameter(name = "User id") Long userId) {
        logger.info("Received request to fetch user by ID: {}", userId);
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            logger.warn("User with ID {} not found", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("User with ID {} fetched successfully", userId);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("Received request to update user: {}", user);
        Optional<User> userUpdated = userService.updateUser(user);
        if (userUpdated.isEmpty()) {
          logger.error("Failed to update user: {}", user);
          return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User updated successfully: {}", userUpdated.get());
        return new ResponseEntity<>(userUpdated.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") @Parameter(name = "User id") Long userId) {
        logger.info("Received request to delete user with ID: {}", userId);
        Boolean result = userService.deleteUser(userId);
        if (!result) {
           logger.warn("Failed to delete user with ID: {}", userId);
           return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User with ID {} deleted successfully", userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserListPage() {
        logger.info("Received request to fetch all users");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("All users fetched successfully. Total users: {}", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
