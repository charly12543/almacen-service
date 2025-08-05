package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    void deleteById(Long id);
    Role update(Role role);

}
