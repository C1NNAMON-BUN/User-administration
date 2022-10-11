package com.nix.zhylina.services.hibernateService;

import com.nix.zhylina.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();

    void create(UserEntity entity);

    void update(UserEntity entity);

    void remove(UserEntity entity);
}
