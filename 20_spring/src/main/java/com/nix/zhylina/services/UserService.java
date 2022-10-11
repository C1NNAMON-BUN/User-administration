package com.nix.zhylina.services;

import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.utils.Constants;
import com.nix.zhylina.utils.ErrorMessagesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

@Component("userDetailsService")
public class UserService implements UserDetailsService {
    private final HibernateUserDao hibernateUserDao;

    @Autowired
    public UserService(HibernateUserDao hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    public UserEntity findByUsername(String login) {
        return hibernateUserDao.findByLogin(login).get();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format(ErrorMessagesConstants.ERROR_USER_NOT_FOUND_INTO_USER_SERVICE, login));
        }
        return new User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(Constants.CONST_AUTHORITY_ROLE_PREFIX + userEntity.getRoleEntity()
                .getName().toUpperCase()));
    }
}
