// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/service/AuthService.java
package com.myeccomerce.eccomerceapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myeccomerce.eccomerceapi.dto.AuthResponseDto;
import com.myeccomerce.eccomerceapi.dto.LoginRequestDto;
import com.myeccomerce.eccomerceapi.dto.RegisterRequestDto;
import com.myeccomerce.eccomerceapi.model.entity.User;
import com.myeccomerce.eccomerceapi.model.enums.Role;
import com.myeccomerce.eccomerceapi.repository.UserRepository;
import com.myeccomerce.eccomerceapi.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public User registerCustomer(RegisterRequestDto request) {
        // Guard Clause: Validar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Construir la entidad cifrando la contraseña con BCrypt (una sola vía)
        User newUser = User.builder()
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .role(Role.ROLE_CUSTOMER) // Por defecto se registra como cliente
                .enabled(true)
                .build();

        return userRepository.save(newUser);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        // Guard Clause: Buscar usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Guard Clause: Validar que la contraseña coincida con el hash de una sola vía
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generar el token JWT incluyendo ID, email y rol
        String token = jwtUtils.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return new AuthResponseDto(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}