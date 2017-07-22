package com.nmuzychuk.blog.controller;

import com.nmuzychuk.blog.model.User;
import com.nmuzychuk.blog.repository.UserRepository;
import com.nmuzychuk.blog.services.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used to get user details.
 */
@RestController
public class ProfileController {

    private final UserRepository userRepository;

    @Autowired
    ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public User readUser(@RequestHeader(value = "Authorization") String authHeader) {
        String username = JwtAuthService.getUsername(authHeader);

        return userRepository.findByUsername(username);
    }

}
