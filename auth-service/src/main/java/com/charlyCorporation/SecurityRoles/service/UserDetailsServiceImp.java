package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.UserSec;
import com.charlyCorporation.SecurityRoles.model.dto.AuthLoginRequestDTO;
import com.charlyCorporation.SecurityRoles.model.dto.AuthResponseDTO;
import com.charlyCorporation.SecurityRoles.repository.IUserSecRepository;
import com.charlyCorporation.SecurityRoles.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserSecRepository repo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec userSec = repo.findUserEntityByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException
                                   ("El usuario :" + username + "no fue encontrado"));

        //GrantedAuthority spring security maneja permisos
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //Tomamos roles y los cambiamos a permisos
        userSec.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        //Agregamos los permisos
        userSec.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(
                        new SimpleGrantedAuthority(permission.getPermissionName())));

        //retornamos el usuario en formato de spring security con los datos de nuestro userSec
        return new User(userSec.getUserName(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isAccountNotLocked(),
                userSec.isCredentialNotExpired(),
                authorityList);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequestDTO){

        //recuperamos nombre de usuario y contraseña
        String username = authLoginRequestDTO.username();
        String password = authLoginRequestDTO.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accesToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username,
                "login succesful",
                         accesToken,
                         true);
        return authResponseDTO;
    }

    public Authentication authenticate (String username, String password) {
        //con esto debo buscar el usuario
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // si no es igual
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username,
                                                       userDetails.getPassword(),
                                                       userDetails.getAuthorities());
    }



}
