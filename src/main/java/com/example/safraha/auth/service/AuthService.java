package com.example.safraha.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.safraha.auth.dto.LoginRequest;
import com.example.safraha.auth.dto.LoginResponse;
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
    	
    	if (userRepository.findByEmail(request.getEmail()).isPresent()) {
    	    throw new RuntimeException("Email already registered");
    	}else if (userRepository.findByPhone(request.getPhone()).isPresent()) {
    	    throw new RuntimeException("Phone already registered");
    	}
    	
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
    
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 🔐 Check password
        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("Invalid email or password");
        }

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        return response;
    }

}

