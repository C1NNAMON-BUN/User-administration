package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.Dao;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.NullFieldValueException;
import com.nix.zhylina.utils.HibernateSessionFactoryUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.nix.zhylina.constants.Constants.SEARCH_ERROR;

/**
 * A class that implements common database functions using the Criteria API
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
public class GenericDao<E> implements Dao<E> {
    private static final Logger LOG = LogManager.getLogger(GenericDao.class);

    private final Class<E> type;

    public GenericDao(Class<E> type) {
        super();
        this.type = type;
    }

    @Override
    public void create(E entity) {
        Transaction transaction;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (NoResultException e) {
            throw new DataProcessingException("Field creation failed!", e);
        }
    }

    @Override
    public void update(E entity) {
        Transaction transaction;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (NoResultException e) {
            throw new DataProcessingException("Field update failed!", e);
        }
    }

    @Override
    public void remove(E entity) {
        Transaction transaction;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DataProcessingException("Field deletion failed!", e);
        }
    }

    @Override
    public List<E> findAll() {
        List<E> entities;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(type);
            Root<E> rootEntry = cq.from(type);
            CriteriaQuery<E> all = cq.select(rootEntry);
            TypedQuery<E> allQuery = session.createQuery(all);
            entities = allQuery.getResultList();
        } catch (NoResultException exception) {
            throw new NullFieldValueException(SEARCH_ERROR.concat(" all fields!"), exception);
        }
        return entities;
    }

    @Override
    public E findById(Long id) {
        E entity;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            entity = session.get(type, id);
        } catch (NoResultException exception) {
            LOG.error(SEARCH_ERROR.concat(" by ID!"), exception);
            return null;
        }
        return entity;
    }
}