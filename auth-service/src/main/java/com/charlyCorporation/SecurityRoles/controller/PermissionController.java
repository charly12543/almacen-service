package com.charlyCorporation.SecurityRoles.controller;


import com.charlyCorporation.SecurityRoles.model.Permission;
import com.charlyCorporation.SecurityRoles.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permission")
@PreAuthorize("denyAll()")
public class PermissionController {

    @Autowired
    private IPermissionService service;

    @PostMapping("/save")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(permission));
    }

    @GetMapping("/allPermission")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Permission>> getAllPermissions(){
        List<Permission> permissionList = service.findALL();
        return ResponseEntity.ok(permissionList);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id){
        Optional<Permission> permission = service.findById(id);
        return permission.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());

    }



}
