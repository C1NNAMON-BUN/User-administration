package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.entitie.Role;
import com.nix.zhylina.exception.NullFieldValueException;
import com.nix.zhylina.utils.HibernateSessionFactoryUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.NoResultException;

import static com.nix.zhylina.constants.Constants.SEARCH_ERROR;

/**
 * A class that implements functions for working with the User table using the HQL
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
public class HibernateRoleDao extends GenericDao<Role> implements RoleDao {
    public HibernateRoleDao() {
        super(Role.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Role findByName(String name) {
        Role role;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query <Role> query = session.createQuery("FROM Role WHERE name= :name") ;
            query.setParameter("name", name);
            role = query.uniqueResult();
        } catch (NoResultException exception) {
            throw new NullFieldValueException(SEARCH_ERROR.concat("!"), exception);
        }
        return role;
    }
}
