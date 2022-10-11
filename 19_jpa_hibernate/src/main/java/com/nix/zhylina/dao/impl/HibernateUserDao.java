package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.NullFieldValueException;
import com.nix.zhylina.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static com.nix.zhylina.constants.Constants.SEARCH_ERROR;

/**
 * A class that implements functions for working with the User table using the Criteria API
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
public class HibernateUserDao extends GenericDao<User> implements UserDao {
    private static final Logger LOG = LogManager.getLogger(HibernateUserDao.class);

    public HibernateUserDao() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        User user;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> roleRoot = criteriaQuery.from(User.class);
            criteriaQuery.where(criteriaBuilder.equal(roleRoot.get("login"), login));
            user = session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            throw new NullFieldValueException(SEARCH_ERROR.concat(" by login!"), exception);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> roleRoot = criteriaQuery.from(User.class);
            criteriaQuery.where(criteriaBuilder.equal(roleRoot.get("email"), email));
            user = session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            throw new NullFieldValueException(SEARCH_ERROR.concat(" by email!"), exception);
        }
        return user;
    }
}
