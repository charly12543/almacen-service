package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.Permission;
import com.charlyCorporation.SecurityRoles.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository repo;


    @Override
    public List<Permission> findALL() {
        return repo.findAll();
    }

    @Override
    public Permission save(Permission permission) {
        return repo.save(permission);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);

    }

    @Override
    public Permission update(Permission permission) {
        return repo.save(permission);
    }
}
