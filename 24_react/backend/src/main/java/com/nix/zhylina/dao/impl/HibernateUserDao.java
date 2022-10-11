package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.utils.SqlQueriesConstants;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
@Transactional
public class HibernateUserDao extends GenericJdbcDao<UserEntity> implements UserDao<UserEntity> {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public void update(UserEntity user) {
        super.update(user);
    }

    @Transactional
    @Override
    public void remove(UserEntity user) {
        super.remove(user);
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FIND_BY_LOGIN_USER, UserEntity.class)
                .setParameter("login", login).uniqueResultOptional();

    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FIND_BY_EMAIL_USER, UserEntity.class)
                .setParameter("email", email).uniqueResultOptional();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FIND_BY_ID_USER, UserEntity.class)
                .setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public List<UserEntity> findAll() {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FROM_USER, UserEntity.class).list();
    }
}
