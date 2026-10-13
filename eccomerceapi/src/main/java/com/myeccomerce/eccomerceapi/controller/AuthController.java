// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/controller/AuthController.java
package com.myeccomerce.eccomerceapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myeccomerce.eccomerceapi.dto.AuthResponseDto;
import com.myeccomerce.eccomerceapi.dto.LoginRequestDto;
import com.myeccomerce.eccomerceapi.dto.RegisterRequestDto;
import com.myeccomerce.eccomerceapi.model.entity.User;
import com.myeccomerce.eccomerceapi.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequestDto request) {
        User registeredUser = authService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}