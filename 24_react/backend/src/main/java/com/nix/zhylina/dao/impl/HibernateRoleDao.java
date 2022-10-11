package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.models.RoleEntity;
import com.nix.zhylina.utils.SqlQueriesConstants;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
@Transactional
public class HibernateRoleDao extends GenericJdbcDao<RoleEntity> implements RoleDao<RoleEntity> {
    private final SessionFactory sessionFactory;

    public HibernateRoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FIND_BY_NAME_ROLE, RoleEntity.class)
                .setParameter("name", name).uniqueResultOptional();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FIND_BY_ID_ROLE, RoleEntity.class)
                .setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public List<RoleEntity> findAll() {
        return sessionFactory.getCurrentSession().createQuery(SqlQueriesConstants.SQL_FROM_ROLE, RoleEntity.class).list();
    }
}
