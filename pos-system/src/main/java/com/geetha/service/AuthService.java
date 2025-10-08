package com.geetha.service;

import com.geetha.exception.UserException;
import com.geetha.payload.dto.UserDto;
import com.geetha.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;

}
