package com.charlyCorporation.SecurityRoles.repository;

import com.charlyCorporation.SecurityRoles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
}
