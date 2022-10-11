package com.nix.zhylina.database;

import com.nix.zhylina.entities.Role;
import com.nix.zhylina.entities.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.jdbc.JdbcRoleDao;
import com.nix.zhylina.jdbc.JdbcUserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Properties;

import static com.nix.zhylina.constants.Constants.DB_CREATION_SCRIPT;
import static com.nix.zhylina.constants.Constants.DB_DROP_SCRIPT;
import static com.nix.zhylina.constants.Constants.DB_INITIALIZATION_SCRIPT;
import static com.nix.zhylina.constants.Constants.PROPERTIES_FILENAME;

/**
 * <p><h2>InitializeDatabase</h2>
 * <b>The class in which the cleanup, creation and initialization of the base
 * takes place data</b> <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class InitializeDatabase {
    private static final Logger LOG =
            LogManager.getLogger(InitializeDatabase.class);

    /**
     * The method in which the initialization of two tables with
     * using the SQL script contained in the path
     * {@link com.nix.zhylina.constants.Constants#DB_INITIALIZATION_SCRIPT}.
     * The method also calls methods from classes {@link JdbcUserDao}
     * and {@link JdbcRoleDao}
     */
    public void initializeBase() {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();
        Role role = new Role("Admin");
        Role roleToRemove = new Role("ToDelete");
        Role roleToUpdate = new Role(1L, "Team Lead");
        User user = new User();
        user.setLogin("login6");
        user.setPassword("312");
        user.setEmail("email");
        user.setFirstName("Paul");
        user.setLastName("Skinner");
        user.setBirthday(new Date(new GregorianCalendar(1970, 0, 1).getTimeInMillis()));
        user.setIdRole(roleToUpdate.getId());
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setLogin("login");
        userToUpdate.setPassword("12345");
        userToUpdate.setEmail("email1@gmail.com");
        userToUpdate.setFirstName("Alex");
        userToUpdate.setLastName("Carter");
        userToUpdate.setBirthday(new Date(new GregorianCalendar(2000, 5, 20).getTimeInMillis()));
        userToUpdate.setIdRole(2L);
        User userToRemove = new User();
        userToRemove.setLogin("loginDelete");
        userToRemove.setPassword("1234512312");
        userToRemove.setEmail("email@gmail.comdelete");
        userToRemove.setFirstName("Gerard");
        userToRemove.setLastName("Hudson");
        userToRemove.setBirthday(new Date(new GregorianCalendar(2000, 5, 20).getTimeInMillis()));
        userToRemove.setIdRole(5L);
        queryExecution(DB_INITIALIZATION_SCRIPT);
        LOG.info("================CREATE ROLE===========");
        jdbcRoleDao.create(role);
        jdbcRoleDao.create(roleToRemove);
        LOG.info("================UPDATE ROLE===========");
        jdbcRoleDao.update(roleToUpdate);
        LOG.info("================FIND ROLE===========");
        jdbcRoleDao.findAll();
        jdbcRoleDao.findById(4L);
        jdbcRoleDao.findByName("Team Lead");
        LOG.info("================REMOVE ROLE===========");
        jdbcRoleDao.remove(roleToRemove);
        LOG.info("\n\n");
        LOG.info("================CREATE USER===========");
        jdbcUserDao.create(user);
        jdbcUserDao.create(userToRemove);
        LOG.info("================UPDATE USER===========");
        jdbcUserDao.update(userToUpdate);
        LOG.info("================REMOVE USER===========");
        jdbcUserDao.remove(userToRemove);
        LOG.info("================FIND USER===========");
        jdbcUserDao.findAll();
        jdbcUserDao.findById(4L);
        jdbcUserDao.findByLogin("login4");
        jdbcUserDao.findByEmail("email3@gmail.com");
    }

    /**
     * The method in which the creation of two tables occurs using
     * SQL script that is contained in the path
     * {@link com.nix.zhylina.constants.Constants#DB_CREATION_SCRIPT}
     */
    public void generateTables() {
        queryExecution(DB_CREATION_SCRIPT);
    }

    /**
     * A method in which two tables are dropped using
     * SQL script that is contained in the path
     * {@link com.nix.zhylina.constants.Constants#DB_DROP_SCRIPT}
     */
    public void dropTables() {
        queryExecution(DB_DROP_SCRIPT);
    }

    /**
     * The method in which the connection to the connection pool occurs
     * {@link DBManager}, SQL script is read and passed to {@code
     * requestPath}, the request is made and connections are closed.
     *
     * @param requestPath contains the path to the SQL script
     * @see DBManager this class describes the implementation of the
     * connection pool
     */
    public void queryExecution(String requestPath) {
        Connection connection = null;
        Statement statement = null;
        Properties prop = PropertiesUtil.getProperty(PROPERTIES_FILENAME);
        try {
            connection = new DBManager().getConnection();
            statement = connection.createStatement();
            InputStream scriptInputStream =
                    InitializeDatabase.class.getClassLoader()
                            .getResourceAsStream(prop.getProperty(requestPath));
            String query = IOUtils.toString(new InputStreamReader
                    (Objects.requireNonNull(scriptInputStream), StandardCharsets.UTF_8));
            statement.execute(query);
        } catch (IOException exception) {
            throw new DataProcessingException("Error reading script!",
                    exception);
        } catch (SQLException exception) {
            throw new DataProcessingException("Error executing request!",
                    exception);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException sqlException) {
                throw new DataProcessingException("Error closing connections!",
                        sqlException);
            }
        }
    }
}