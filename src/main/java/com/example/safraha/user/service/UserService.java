package com.example.safraha.user.service;

import org.springframework.stereotype.Service;

import com.example.safraha.user.entity.User;
import com.example.safraha.user.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }
}
