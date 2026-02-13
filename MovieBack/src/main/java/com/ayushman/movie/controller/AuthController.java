package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.UserRequest;
import com.ayushman.movie.dto.response.AuthResponse;
import com.ayushman.movie.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}