package com.shipracoding.introduction.services;

import com.shipracoding.introduction.dto.LoginDto;
import com.shipracoding.introduction.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDto loginDto) {
    Authentication authentication =  authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
              );
    //   this now holds authenticated user details.
        UserEntity user =(UserEntity) authentication.getPrincipal();
        return jwtService.generateToken(user); // or ab isko use karke token bana de rahe hain

    }
}
