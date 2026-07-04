package com.shipracoding.introduction.controllers;

import com.shipracoding.introduction.dto.LoginDto;
import com.shipracoding.introduction.dto.SignUpDto;
import com.shipracoding.introduction.dto.UserDto;
import com.shipracoding.introduction.services.AuthService;
import com.shipracoding.introduction.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
      UserDto userDto = userService.signUp(signUpDto);
      return ResponseEntity.ok(userDto);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto,
                                        HttpServletResponse response){
        String token = authService.login(loginDto);

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }
    // this will return a token (string)
}
