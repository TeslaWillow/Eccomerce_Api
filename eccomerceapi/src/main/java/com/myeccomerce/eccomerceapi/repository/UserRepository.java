// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/repository/UserRepository.java
package com.myeccomerce.eccomerceapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myeccomerce.eccomerceapi.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email1
    Optional<User> findByEmail(String email);

    // Check if an user with the same email exists
    boolean existsByEmail(String email);
}