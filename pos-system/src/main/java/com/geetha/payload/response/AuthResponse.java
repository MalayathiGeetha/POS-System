package com.geetha.payload.response;

import com.geetha.payload.dto.UserDto;
import lombok.Data;


@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto user;
}
