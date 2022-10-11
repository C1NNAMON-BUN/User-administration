package com.nix.zhylina.jdbc;

import com.nix.zhylina.dao.GenericJdbcDao;
import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.entities.Role;
import com.nix.zhylina.exception.RoleDataProcessingException;
import com.nix.zhylina.exception.UserDataProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.nix.zhylina.constants.Constants.SQL_ADD_ROLE;
import static com.nix.zhylina.constants.Constants.SQL_DELETE_ROLE;
import static com.nix.zhylina.constants.Constants.ERROR_CREATION_TUPLE;
import static com.nix.zhylina.constants.Constants.ERROR_REMOVING_TUPLE;
import static com.nix.zhylina.constants.Constants.ERROR_SEARCH_TUPLE;
import static com.nix.zhylina.constants.Constants.ERROR_UPDATE_TUPLE;
import static com.nix.zhylina.constants.Constants.SQL_FIND_BY_ID_ROLE;
import static com.nix.zhylina.constants.Constants.SQL_FIND_BY_NAME;
import static com.nix.zhylina.constants.Constants.SQL_SELECT_ROLE;
import static com.nix.zhylina.constants.Constants.SQL_UPDATE_ROLE;

/**
 * <p><h2>JdbcRoleDao</h2>
 * <b>A class that queries the RoleDao table using JDBC </b>
 * <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class JdbcRoleDao extends GenericJdbcDao<Role> implements RoleDao {
    private static final Logger LOG = LogManager.getLogger(JdbcRoleDao.class);
    private ResultSet resultSet = null;

    /**
     * This method writes the object to the database and using the method
     * {@link JdbcRoleDao#findByName(String)} is being searched
     * by {@code name} of an object in order to read the ID and write it to
     * the object {@code entity}
     *
     * @param entity the object that will be written to the database. ID
     *               object must not be specified
     */
    @Override
    public void create(Role entity) {
        try {
            preparedStatement = executeStatement(SQL_ADD_ROLE);
            preparedStatement.setString(1, entity.getName());
            closeUpdate();
            LOG.info("Database entry created" + entity);
            entity.setId(findByName(entity.getName()).getId());
        } catch (SQLException sqlException) {
            throw new RoleDataProcessingException(ERROR_CREATION_TUPLE, sqlException);
        }
    }

    /**
     * This method performs data changes in a tuple using a query.
     * The search for the required tuple is performed by {@code ID} of the
     * object
     *
     * @param entity the object that contains the data to update
     */
    @Override
    public void update(Role entity) {
        try {
            preparedStatement = executeStatement(SQL_UPDATE_ROLE);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());
            closeUpdate();
            LOG.info("Database entry updated: " + entity);
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(ERROR_UPDATE_TUPLE, sqlException);
        }
    }

    /**
     * This method performs the deletion of a record from the database.
     * Record search, which needs to be removed occurs by {@code name} of the
     * object
     *
     * @param entity the object that contains the data to update
     */
    @Override
    public void remove(Role entity) {
        try {
            preparedStatement = executeStatement(SQL_DELETE_ROLE);
            preparedStatement.setString(1, entity.getName());
            closeUpdate();
            LOG.info("Database entry deleted: " + entity);
        } catch (SQLException sqlException) {
            throw new RoleDataProcessingException(ERROR_REMOVING_TUPLE, sqlException);
        }
    }

    /**
     * This method reads all objects that are contained in
     * database
     *
     * @return a list of objects read from the database
     */
    @Override
    public List<Role> findAll() {
        List<Role> listUsers = new ArrayList<>();
        try {
            preparedStatement = executeStatement(SQL_SELECT_ROLE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUsers.add(new Role(resultSet.getLong(1),
                        resultSet.getString(2)));
            }
            connection.commit();
        } catch (SQLException sqlException) {
            throw new RoleDataProcessingException(ERROR_SEARCH_TUPLE, sqlException);
        } finally {
            closeResultSet(resultSet);
            close();
        }
        LOG.info("Found roles: " + listUsers);
        return listUsers;
    }

    /**
     * This method searches for a database record by ID
     *
     * @param id entry number
     * @return an object read from the database
     */
    @Override
    public Role findById(Long id) {
        LOG.info("Found record by id: ");
        return findRole(SQL_FIND_BY_ID_ROLE, id.toString());
    }

    /**
     * This method searches for a database entry by name
     *
     * @param name the object with the given name must be found in the database
     * @return an object read from the database
     */
    @Override
    public Role findByName(String name) {
        LOG.info("Found entry by name: ");
        return findRole(SQL_FIND_BY_NAME, name);
    }

    /**
     * The method in which the request {@code inquiry} is executed and
     * read data equivalent to the given {@code findElement}.
     *
     * @param inquiry     query to be executed
     * @param findElement data to be inserted into the request
     * @return an object read from the database
     */
    private Role findRole(String inquiry, String findElement) {
        Role entity = new Role();
        try {
            preparedStatement = executeStatement(inquiry);
            preparedStatement.setString(1, findElement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity = new Role(resultSet.getLong(1), resultSet.getString(2));
                LOG.info(entity);
            }
            connection.commit();
        } catch (SQLException sqlException) {
            throw new RoleDataProcessingException(ERROR_SEARCH_TUPLE, sqlException);
        } finally {
            closeResultSet(resultSet);
            close();
        }
        return entity;
    }
}