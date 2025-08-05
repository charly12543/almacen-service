package com.charlyCorporation.SecurityRoles.repository;

import com.charlyCorporation.SecurityRoles.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserSecRepository extends JpaRepository<UserSec, Long> {

    Optional<UserSec> findUserEntityByUserName(String name);
}
