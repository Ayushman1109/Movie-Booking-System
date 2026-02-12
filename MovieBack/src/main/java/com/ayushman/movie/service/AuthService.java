package com.ayushman.movie.service;

import com.ayushman.movie.dto.request.UserRequest;
import com.ayushman.movie.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    public User registerUser(UserRequest userRequest){
        return null;
    }


    public User loginUser(UserRequest userRequest){
        return null;
    }

}
