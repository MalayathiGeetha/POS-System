package com.geetha.controller;

import com.geetha.exception.UserException;
import com.geetha.mapper.UserMapper;
import com.geetha.modal.User;
import com.geetha.payload.dto.UserDto;
import com.geetha.repository.UserRepository;
import com.geetha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication){
        User savedUser = userRepository.findByEmail(authentication.getName());
        if(savedUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,@PathVariable Long id
    ) throws UserException, Exception {
        User user=userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
