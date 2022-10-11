package com.nix.zhylina.services;

import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


import static com.nix.zhylina.utils.Constants.CONST_AUTHORITY_ROLE_PREFIX;
import static com.nix.zhylina.utils.ErrorMessagesConstants.ERROR_USER_NOT_FOUND_INTO_USER_SERVICE;

@Component("userDetailsService")
public class UserService implements UserDetailsService {
    private final HibernateUserDao hibernateUserDao;

    @Autowired
    public UserService(HibernateUserDao hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return hibernateUserDao.findByLogin(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(username).orElse(null);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format(ERROR_USER_NOT_FOUND_INTO_USER_SERVICE, username));
        }
        return new User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(CONST_AUTHORITY_ROLE_PREFIX + userEntity.getRoleEntity()
                .getName().toUpperCase()));
    }
}
