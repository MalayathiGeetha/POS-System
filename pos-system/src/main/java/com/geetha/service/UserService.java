package com.geetha.service;

import com.geetha.exception.UserException;
import com.geetha.modal.User;

import java.util.List;

public interface UserService {
    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers();
}
