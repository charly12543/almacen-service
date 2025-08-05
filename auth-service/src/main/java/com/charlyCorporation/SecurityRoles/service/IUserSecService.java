package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserSecService{

    List<UserSec> findAll();
    Optional<UserSec> finById(Long id);
    void deleteById(Long id);
    UserSec save(UserSec user);
    UserSec update(UserSec user);
    String encriptPassword(String password);
}
