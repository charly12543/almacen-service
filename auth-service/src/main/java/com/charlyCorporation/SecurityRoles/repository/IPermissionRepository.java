package com.charlyCorporation.SecurityRoles.repository;

import com.charlyCorporation.SecurityRoles.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IPermissionRepository extends JpaRepository<Permission,Long> {


    
}
