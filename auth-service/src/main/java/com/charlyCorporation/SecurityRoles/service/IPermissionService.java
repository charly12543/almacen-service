package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {


        List<Permission> findALL();
        Permission save(Permission permission);
        Optional<Permission> findById(Long id);
        void deleteById(Long id);
        Permission update(Permission permission);


}
