package com.nix.zhylina.dao;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public interface UserDao<UserEntity> extends Dao<UserEntity> {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();
}
