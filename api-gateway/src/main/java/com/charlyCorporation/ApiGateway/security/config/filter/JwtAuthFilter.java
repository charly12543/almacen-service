package com.charlyCorporation.ApiGateway.security.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter {

    @Value("${security.jwt.private.key}")
    private String jwtSecret;

    @Value("${security.jwt.user.generator}")
    private String jwtIssuer;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> openPatterns = List.of(
            "/login",
            "/register",
            "/oauth2/**",
            "/login/oauth2/**",
            "/error",
            "/eureka/**"
    );



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        System.out.println("📍 PATH recibido: '" + path + "'");

        boolean isOpen = openPatterns.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        System.out.println("🔓 ¿Ruta pública reconocida?: " + isOpen);

        if (isOpen) {
            System.out.println("✅ Ruta pública, continúa sin token");
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No hay token válido en Authorization Header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7); // Elimina "Bearer "

        System.out.println("🔑 Token recibido: " + token);

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            System.out.println("✅ Token JWT verificado correctamente. Usuario: " + decodedJWT.getSubject());

            return chain.filter(exchange);

        } catch (JWTVerificationException e) {
            System.out.println("❌ Error verificando token: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
