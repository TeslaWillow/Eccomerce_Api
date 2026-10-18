// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/security/JwtAuthenticationFilter.java
package com.myeccomerce.eccomerceapi.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException,
            IOException {

        // Review Authorization Header for Bearer Token
        String headerAuth = request.getHeader("Authorization");

        // Guard Clause: Si no trae el header Bearer, continúa con la cadena sin
        // autenticar
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continue with the chain without authentication
            return;
        }
        // Get token from header
        String token = headerAuth.substring(7);
        // Validate token
        if (jwtUtils.validateToken(token)) {
            Claims claims = jwtUtils.getAllClaimsFromToken(token);
            String email = claims.getSubject();
            String role = claims.get("role", String.class);
            // Extraemos el rol del token

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Mapear el rol a una autoridad de Spring Security
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Registrar la autenticación y el rol en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response); // Continue with the chain for next filter
    }
}