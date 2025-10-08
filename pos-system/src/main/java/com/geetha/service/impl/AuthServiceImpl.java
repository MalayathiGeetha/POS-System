package com.geetha.service.impl;

import com.geetha.configuration.JwtProvider;
import com.geetha.domain.UserRole;
import com.geetha.exception.UserException;
import com.geetha.mapper.UserMapper;
import com.geetha.modal.User;
import com.geetha.payload.dto.UserDto;
import com.geetha.payload.response.AuthResponse;
import com.geetha.repository.UserRepository;
import com.geetha.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {

        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new UserException("Email id already registered!!");
        }
        if (userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role ADMIN is not allowed!");
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);
        if (savedUser == null) throw new UserException("User registration failed");

        // Authenticate properly
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;
    }


    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email= userDto.getEmail();
        Authentication authentication = authenticate(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.iterator().next().getAuthority();
        String jwt=jwtProvider.generateToken(authentication);
        User user=userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        authResponse.setUser(UserMapper.toDTO(user));

        return authResponse;
    }


    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails= customUserImplementation.loadUserByUsername(email);
        if(userDetails==null){
            throw new UserException("email id doesn't exist"+email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
