// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/service/UserService.java
package com.myeccomerce.eccomerceapi.service;

import org.springframework.stereotype.Service;

import com.myeccomerce.eccomerceapi.dto.UpdateRoleRequestDto;
import com.myeccomerce.eccomerceapi.model.entity.User;
import com.myeccomerce.eccomerceapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User updateUserRole(Long userId, UpdateRoleRequestDto request) {
        // Guard Clause: Validar que el usuario exista
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Actualizar el rol y guardar
        user.setRole(request.getRole());
        return userRepository.save(user);
    }
}