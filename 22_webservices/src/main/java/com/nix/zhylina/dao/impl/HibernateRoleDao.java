package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.models.RoleEntity;
import com.nix.zhylina.utils.SqlQueriesConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
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
        try (Session session = sessionFactory.openSession()) {
            Query<RoleEntity> query = session.createQuery(SqlQueriesConstants.SQL_FIND_BY_NAME_ROLE, RoleEntity.class);
            query.setParameter("name", name);

            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<RoleEntity> query = session.createQuery(SqlQueriesConstants.SQL_FIND_BY_ID_ROLE, RoleEntity.class);
            query.setParameter("id", id);

            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<RoleEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
            Root<RoleEntity> usersRoot = criteriaQuery.from(RoleEntity.class);
            CriteriaQuery<RoleEntity> all = criteriaQuery.select(usersRoot);
            TypedQuery<RoleEntity> allUsers = session.createQuery(all);

            return allUsers.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
