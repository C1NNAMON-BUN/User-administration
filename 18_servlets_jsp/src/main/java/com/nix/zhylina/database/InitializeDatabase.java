package com.nix.zhylina.database;

import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.dao.impl.JdbcRoleDao;
import com.nix.zhylina.dao.impl.JdbcUserDao;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

import static com.nix.zhylina.constants.Constants.DB_INITIALIZATION_SCRIPT;
import static com.nix.zhylina.constants.Constants.PROPERTIES_FILENAME;

/**
 * <p><h2>InitializeDatabase</h2>
 * <b>The class in which the cleanup, creation and initialization of the base
 * takes place data</b> <br>
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 18.02.2022 </p>
 */
public class InitializeDatabase {

    /**
     * The method in which the initialization of two tables with
     * using the SQL script contained in the path
     * The method also calls methods from classes {@link JdbcUserDao}
     * and {@link JdbcRoleDao}
     */
    public void initializeBase() {
        queryExecution(DB_INITIALIZATION_SCRIPT);
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