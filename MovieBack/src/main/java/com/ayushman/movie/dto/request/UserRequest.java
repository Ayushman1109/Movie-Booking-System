package com.ayushman.movie.dto.request;

import com.ayushman.movie.entity.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String email;
    private Role role;
}
