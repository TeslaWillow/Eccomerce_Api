// ./src/main/java/com/myeccomerce/api/controller/UserController.java
package com.myeccomerce.eccomerceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myeccomerce.eccomerceapi.dto.UpdateRoleRequestDto;
import com.myeccomerce.eccomerceapi.model.entity.User;
import com.myeccomerce.eccomerceapi.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRoleRequestDto request) {
        
        User updatedUser = userService.updateUserRole(userId, request);
        return ResponseEntity.ok(updatedUser);
    }
}