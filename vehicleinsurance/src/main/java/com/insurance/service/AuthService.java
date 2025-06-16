package com.insurance.service;

import com.insurance.dto.LoginRequest;
import com.insurance.dto.RegisterRequest;
import com.insurance.entity.Role;
import com.insurance.entity.User;
import com.insurance.repository.UserRepository;
import com.insurance.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .dob(request.getDob())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return jwtUtil.generateToken(request.getEmail());
    }
}
