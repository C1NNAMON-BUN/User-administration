package com.nix.zhylina.dao.impl;

import com.nix.zhylina.constants.Constants;
import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.UserDataProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><h2>JdbcUserDao</h2>
 * <b>A class that queries the UserDao table using JDBC </b>
 * <br>
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */
public class JdbcUserDao extends GenericJdbcDao<User> implements UserDao {
    private static final Logger LOG = LogManager.getLogger(JdbcUserDao.class);
    private ResultSet resultSet = null;

    /**
     * This method writes the object to the database and using the method
     * {@link JdbcUserDao#findByEmail(String)} is being searched
     * by {@code email} of the object in order to read the ID and write it to
     * the object {@code entity}
     *
     * @param entity the object that will be written to the database. ID
     *               object must not be specified
     */
    @Override
    public void create(User entity) {
        try {
            preparedStatement = executeStatement(Constants.SQL_ADD_USER);
            substitutionOfValuesInQuery(entity);
            closeUpdate();
            LOG.info("Database entry created: " + entity);
            entity.setId(findByEmail(entity.getEmail()).getId());
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(Constants.ERROR_CREATION_TUPLE, sqlException);
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
    public void update(User entity) {
        try {
            preparedStatement = executeStatement(Constants.SQL_UPDATE_USER);
            substitutionOfValuesInQuery(entity);
            preparedStatement.setLong(8, entity.getId());
            closeUpdate();
            LOG.info("Database entry updated:" + entity);
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(Constants.ERROR_UPDATE_TUPLE, sqlException);
        }
    }

    /**
     * This method contains data that will be substituted
     * instead of wildcard characters in SQL query
     *
     * @param entity the object whose data is to be read
     * @throws SQLException if the data cannot be read
     */
    private void substitutionOfValuesInQuery(User entity) throws SQLException {
        preparedStatement.setString(1, entity.getLogin());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getFirstName());
        preparedStatement.setString(5, entity.getLastName());
        preparedStatement.setDate(6, entity.getBirthday());
        preparedStatement.setLong(7, entity.getIdRole());
    }

    /**
     * This method performs the deletion of a record from the database.
     * Record search, which needs to be removed occurs by {@code name} of the
     * object
     *
     * @param entity the object that contains the data to update
     */
    @Override
    public void remove(User entity) {
        try {
            preparedStatement = executeStatement(Constants.SQL_REMOVE_USER);
            preparedStatement.setString(1, (entity.getEmail()));
            closeUpdate();
            LOG.info("Database entry removed: " + entity);
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(Constants.ERROR_REMOVING_TUPLE, sqlException);
        }
    }

    /**
     * This method reads all objects that are contained in
     * database
     *
     * @return a list of objects read from the database
     */
    @Override
    public List<User> findAll() {
        List<User> listUsers = new ArrayList<>();
        try {
            preparedStatement = executeStatement(Constants.SQL_SELECT_USER);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUsers.add(newUser(resultSet));
            }
            connection.commit();
            LOG.info("Found entries in the database: " + listUsers);
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(Constants.ERROR_SEARCH_TUPLE, sqlException);
        } finally {
            closeResultSet(resultSet);
            close();
        }
        return listUsers;
    }

    /**
     * This method searches for a database record by ID
     *
     * @param id entry number
     * @return an object read from the database
     */
    @Override
    public User findById(Long id) {
        LOG.info("Found record by id: ");
        return findUser(Constants.SQL_FIND_BY_ID_USER, id.toString());
    }

    /**
     * This method searches for a database record by login
     *
     * @param login login that will be used to search for an entry in
     *              database
     * @return an object read from the database
     */
    @Override
    public User findByLogin(String login) {
        LOG.info("Found entry by login: ");
        return findUser(Constants.SQL_FIND_BY_LOGIN, login);
    }

    /**
     * This method searches for a database record by email
     *
     * @param email The email that will be used to search for a record in
     *              database
     * @return an object read from the database
     */
    @Override
    public User findByEmail(String email) {
        LOG.info("Found record by email: ");
        return findUser(Constants.SQL_FIND_BY_EMAIL, email);
    }

    /**
     * The method in which the request {@code inquiry} is executed and
     * read data equivalent to the given {@code findElement}.
     *
     * @param inquiry     query to be executed
     * @param findElement data to be inserted into the request
     * @return an object read from the database
     */
    private User findUser(String inquiry, String findElement) {
        User user = null;
        try {
            preparedStatement = executeStatement(inquiry);
            preparedStatement.setString(1, findElement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = newUser(resultSet);
                LOG.info(user);
            }
            connection.commit();
        } catch (SQLException sqlException) {
            throw new UserDataProcessingException(Constants.ERROR_SEARCH_TUPLE, sqlException);
        } finally {
            closeResultSet(resultSet);
            close();
        }
        return user;
    }

    /**
     * The method of creating an object of the class {@link User}, putting
     * data into it retrieved from the database and stored in {@code resultSet}
     *
     * @param resultSet of the data that was read from the database
     * @return new class object {@link User}
     * @throws SQLException if data cannot be retrieved from
     *                      {@code resultSet}
     */
    private User newUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getDate(7),
                resultSet.getLong(8));
    }
}