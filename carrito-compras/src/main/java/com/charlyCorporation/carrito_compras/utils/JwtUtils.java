package com.charlyCorporation.carrito_compras.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.private.key}")
    private String privatekey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    //Metodo para la creacion de Tokens
    public String createToken(Authentication authentication){

        Algorithm algorithm = Algorithm.HMAC256(privatekey);

        //
        String username = authentication.getPrincipal().toString();


        //Generamos el token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) //Usuario generado por el token
                .withSubject(username) //A quien se le genea el token
                .withClaim("authorities", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))  //Datos o informacion contraido en el JWT
                .withIssuedAt(new Date()) // Fecha de generacion del token
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60000))) //Fecha de expiracion 30min
                .withJWTId(UUID.randomUUID().toString()) //Id al token random que se genera
                .withNotBefore(new Date(System.currentTimeMillis())) //Desde que fecha es valido
                .sign(algorithm); //La firma es creada con nuestra clave secreta

        return jwtToken;
    }

    //Metodo para decodificar
    public DecodedJWT validateToken(String token){

        try {

            Algorithm algorithm = Algorithm.HMAC256(privatekey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        }
        catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid Token, Not Authorized");
        }


    }

    //Metodo para obtener el nombre de usuario del token
    public String extractUsername(DecodedJWT decodedJWT){
        //El subject es el usuario que establecimos al crear el token
        return decodedJWT.getSubject().toString();
    }

    //Metodo para obtener un Claim en especifico
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //Metodo para obtener todos los Claims
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }


}
