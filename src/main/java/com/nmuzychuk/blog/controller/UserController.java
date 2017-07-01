package com.nmuzychuk.blog.controller;

import com.nmuzychuk.blog.exceptions.NotFoundException;
import com.nmuzychuk.blog.model.User;
import com.nmuzychuk.blog.repository.UserRepository;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Collection<User> readUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    User readUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId);

        if (user != null) {
            return user;
        } else {
            throw new NotFoundException(User.class.getSimpleName(), userId);
        }
    }

}
