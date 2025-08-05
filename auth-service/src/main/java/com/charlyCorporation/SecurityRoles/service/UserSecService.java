package com.charlyCorporation.SecurityRoles.service;

import com.charlyCorporation.SecurityRoles.model.UserSec;
import com.charlyCorporation.SecurityRoles.repository.IUserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSecService implements IUserSecService{

    @Autowired
    private IUserSecRepository repo;


    @Override
    public List<UserSec> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<UserSec> finById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);

    }

    @Override
    public UserSec save(UserSec user) {
        return repo.save(user);
    }

    @Override
    public UserSec update(UserSec user) {
        return repo.save(user);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
