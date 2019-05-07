package com.quizorus.backend.controller;

import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.payload.JwtAuthenticationResponse;
import com.quizorus.backend.payload.LoginRequest;
import com.quizorus.backend.payload.SignUpRequest;
import com.quizorus.backend.repository.RoleRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.JwtTokenProvider;
import com.quizorus.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
