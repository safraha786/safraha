package com.example.safraha.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.safraha.auth.dto.SignupRequest;
import com.example.safraha.auth.dto.SignupResponse;
import com.example.safraha.user.entity.User;
import com.example.safraha.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponse signup(SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); //Bcrypt now
        user.setPhone(request.getPhone());
        user.setRole("USER");

        User savedUser = userRepository.save(user);
        
        SignupResponse resp = new SignupResponse();
        resp.setEmail(savedUser.getEmail());
        resp.setId(savedUser.getId());
        resp.setName(savedUser.getName());
        resp.setPhone(savedUser.getPhone());
     
        return resp;
        
    }
}

