package com.example.safraha.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.safraha.user.dto.UserResponse;
import com.example.safraha.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getMe(Authentication authentication) {

        String email = authentication.getName(); // from JWT
        return userService.getCurrentUser(email);
    }
}
