package com.ayushman.movie.controller;

import com.ayushman.movie.dto.request.UserRequest;
import com.ayushman.movie.entity.User;
import com.ayushman.movie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {

    @Autowired
    private AuthService authService;
    private User user = null;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequest userRequest){
        user = authService.registerUser(userRequest);
        return user;
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserRequest userRequest){
        user = authService.loginUser(userRequest);
        return user;
    }
}
