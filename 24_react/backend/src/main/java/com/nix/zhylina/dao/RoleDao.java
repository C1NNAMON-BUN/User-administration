package com.nix.zhylina.dao;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public interface RoleDao<RoleEntity> {

    Optional<RoleEntity> findById(Long id);

    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> findAll();

}
