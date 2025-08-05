package com.charlyCorporation.SecurityRoles.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {



    @GetMapping("/noSeguro")
    @PreAuthorize("permitAll()")
    public String noSeguro() {
        return "End-point no seguro";
    }

    @GetMapping("/seguro")
    @PreAuthorize("isAuthenticated()")
    public String seguro() {
        return "End-point SEGURO: Solo Roles con perfil ADMIN";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "End-point SEGURO: Solo Roles con perfil USER";
    }





}
