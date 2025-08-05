package com.charlyCorporation.SecurityRoles.security.config;

import com.charlyCorporation.SecurityRoles.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

        private final JwtUtils jwtUtils;

        public CustomOAuth2SuccessHandler(JwtUtils jwtUtils) {
            this.jwtUtils = jwtUtils;
        }

        @Override
        public void onAuthenticationSuccess(
                jakarta.servlet.http.HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication
        ) throws IOException {

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // 🧠 Determinar proveedor (GitHub o Google)
            String provider = request.getRequestURI().contains("github") ? "github" :
                    request.getRequestURI().contains("google") ? "google" : "unknown";

            String username = null;

            if ("github".equals(provider)) {
                username = oAuth2User.getAttribute("login"); // GitHub
            } else if ("google".equals(provider)) {
                username = oAuth2User.getAttribute("email"); // Google: puedes usar "sub", "email", o "name"
            }

            if (username == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "No se pudo obtener el nombre de usuario desde OAuth2 (" + provider + ")");
                return;
            }

            Authentication fakeAuth = new UsernamePasswordAuthenticationToken(
                    username, null, oAuth2User.getAuthorities()
            );

            String token = jwtUtils.createToken(fakeAuth);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setHeader("Authorization", "Bearer " + token);

            response.getWriter().write("""
            {
              "token": "%s",
              "username": "%s",
              "provider": "%s",
              "message": "Autenticación OAuth2 exitosa"
            }
            """.formatted(token, username, provider));
        }
    }
