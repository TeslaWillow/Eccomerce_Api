// ./eccomerceapi/src/main/java/com/myeccomerce/api/controller/TestController.java
package com.myeccomerce.eccomerceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok("Hola " + email);
    }
}