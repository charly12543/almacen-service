package com.charlyCorporation.SecurityRoles.controller;

import com.charlyCorporation.SecurityRoles.model.Role;
import com.charlyCorporation.SecurityRoles.model.UserSec;
import com.charlyCorporation.SecurityRoles.service.IRoleService;
import com.charlyCorporation.SecurityRoles.service.IUserSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("denyAll()")
public class UserSecController {

    @Autowired
    private IUserSecService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserSec>> getAllUser(){
        List<UserSec> userSecList = userService.findAll();
        return ResponseEntity.ok(userSecList);

    }

    @GetMapping("/find/{id_user}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity getUserById(@PathVariable Long id){
        Optional<UserSec> user = userService.finById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity createUser(@RequestBody UserSec user){
        Set<Role> roleList = new HashSet<>();
        Role readRole;

        //Encriptamos contraseña
        user.setPassword(userService.encriptPassword(user.getPassword()));

        for (Role role : user.getRoleList()){
            readRole = roleService.findById(role.getId()).orElse(null);
            if(readRole != null){
                //Si es diferente de null, se guarda en la lista
                roleList.add(readRole);
            }
        }

        if(!roleList.isEmpty()){
            user.setRoleList(roleList);

            UserSec newUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }

        return ResponseEntity.badRequest().build();
    }


}
