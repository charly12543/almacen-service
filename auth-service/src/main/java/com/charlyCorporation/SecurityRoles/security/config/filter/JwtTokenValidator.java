package com.charlyCorporation.SecurityRoles.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.charlyCorporation.SecurityRoles.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;


    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        String path = request.getServletPath();

        // ⛔ Ignorar rutas de login OAuth2
        if (path.startsWith("/auth/oauth2/") || path.startsWith("/auth/login/oauth2/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 👉 Si no hay token o no es Bearer, no validar nada, solo seguir la cadena
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.extractUsername(decodedJWT);
            List<String> roles = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asList(String.class);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // 👉 Si el token es inválido, no bloquear la ruta, solo dejar sin autenticación
            System.out.println("Token inválido: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }



}