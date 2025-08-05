package com.charlyCorporation.SecurityRoles.controller;


import com.charlyCorporation.SecurityRoles.model.dto.AuthLoginRequestDTO;
import com.charlyCorporation.SecurityRoles.model.dto.AuthResponseDTO;
import com.charlyCorporation.SecurityRoles.service.UserDetailsServiceImp;
import com.charlyCorporation.SecurityRoles.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImp imp;

    @Autowired
    private JwtUtils jwtUtils;


    /*Probando git*/
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest){
        return new ResponseEntity<>(imp.loginUser(userRequest), HttpStatus.OK);



    }


}
