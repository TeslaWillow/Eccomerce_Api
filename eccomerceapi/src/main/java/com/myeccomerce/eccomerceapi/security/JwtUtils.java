// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi.security/JwtUtils.java
package com.myeccomerce.eccomerceapi.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component // Marks this class as a Spring component = bean
public class JwtUtils {

    // Secret key for signing the token (in production use a robust environment variable)
    private final String jwtSecret = "your-very-secure-and-long-secret-key-for-jwt-signing-ecommerce-api";
    // 1 day
    private final long jwtExpirationMs = 1 * 24 * 60 * 60 * 1000; 

    private SecretKey _getSigningKey() { // Get signing key
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Long userId, String email, String role) { // Generate token with claims
        Map<String, Object> claims = new HashMap<>(); // Map to store claims
        claims.put("userId", userId); // Put user ID
        claims.put("role", role); // Put role

        return Jwts.builder()
                .claims(claims) // Claims
                .subject(email) // Subject
                .issuedAt(new Date()) // Issued at
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Expiration
                .signWith(this._getSigningKey()) // Signing key
                .compact(); // Compact
                
    }

    public Claims getAllClaimsFromToken(String token) { // Get all claims from token
        return Jwts.parser() // Parse token
                .verifyWith(this._getSigningKey()) // Signing key
                .build()
                .parseSignedClaims(token) // Parse signed claims
                .getPayload(); // Get payload
    }

    public String getEmailFromToken(String token) { // Get email from token
        return getAllClaimsFromToken(token).getSubject(); // Get subject (email)
    }

    public Long getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) { // Validate token
        try {
            Jwts.parser().verifyWith(this._getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException
                | SecurityException | IllegalArgumentException e) {
            return false;
        }
    }
}