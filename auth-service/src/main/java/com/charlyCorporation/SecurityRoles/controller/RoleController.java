package com.charlyCorporation.SecurityRoles.controller;

import com.charlyCorporation.SecurityRoles.model.Permission;
import com.charlyCorporation.SecurityRoles.model.Role;
import com.charlyCorporation.SecurityRoles.service.IPermissionService;
import com.charlyCorporation.SecurityRoles.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("denyAll()")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roleList = roleService.findAll();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id){
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @PostMapping("/save")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Set<Permission> permissionList = new HashSet<Permission>();
        Permission readPermission;

        //Recuperamos los permisos de la base de datos por ID usando un ciclo foreach
        for (Permission p : role.getPermissionList()) {
            permissionService.findById(p.getId()).ifPresent(permissionList::add);
            if (permissionList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);  // O un mensaje personalizado
            }
        }

        role.setPermissionList(permissionList);
        Role newRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);

    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long id,
                                     @RequestBody Role updateRole){

        Role newRole = roleService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El rol con ID "
                        + id + " no se encontró."));


        if (updateRole.getRole() != null) {
            newRole.setRole(updateRole.getRole());
        }
        if (updateRole.getPermissionList() != null) {
            newRole.setPermissionList(updateRole.getPermissionList());
        }

        return ResponseEntity.ok(roleService.save(newRole));
    }
}
