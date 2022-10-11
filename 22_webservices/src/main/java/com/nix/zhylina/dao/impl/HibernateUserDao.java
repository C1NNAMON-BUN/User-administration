package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.utils.SqlQueriesConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
        try (Session session = sessionFactory.openSession()) {
            Query<UserEntity> query = session.createQuery(SqlQueriesConstants.SQL_FIND_BY_LOGIN_USER, UserEntity.class);
            query.setParameter("login", login);

            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<UserEntity> query = session.createQuery(SqlQueriesConstants.SQL_FIND_BY_EMAIL_USER, UserEntity.class);
            query.setParameter("email", email);

            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<UserEntity> query = session.createQuery(SqlQueriesConstants.SQL_FIND_BY_ID_USER, UserEntity.class);
            query.setParameter("id", id);

            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
            Root<UserEntity> usersRoot = criteriaQuery.from(UserEntity.class);
            CriteriaQuery<UserEntity> all = criteriaQuery.select(usersRoot);
            TypedQuery<UserEntity> allUsers = session.createQuery(all);

            return allUsers.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
