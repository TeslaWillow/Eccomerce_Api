// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/security/JwtAuthenticationFilter.java
package com.myeccomerce.eccomerceapi.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
            String email = jwtUtils.getEmailFromToken(token);

            // Create Authentication object for Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email, null, Collections.emptyList()
            );
            // Almacena esa información dentro del objeto de autenticación.
            authentication.setDetails(
                // Analiza la petición y extrae información contextual, 
                // principalmente la dirección IP de origen del cliente y el ID de sesión (si existiera).
                new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Establecer el usuario autenticado en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response); // Continue with the chain for next filter
    }
}