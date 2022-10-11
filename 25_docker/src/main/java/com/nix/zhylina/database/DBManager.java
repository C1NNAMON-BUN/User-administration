package com.nix.zhylina.database;

import com.nix.zhylina.exception.DataProcessingException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.nix.zhylina.constants.Constants.PROPERTIES_FILENAME;


/**
 * <p><h2>DBManager</h2>
 * <b>A class that implements the connection pool to which
 * connect users</b> <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class DBManager {
    private static final Logger LOG = LogManager.getLogger(DBManager.class);
    private static BasicDataSource dataSource;

    /**
     * Method for creating an object {@link BasicDataSource} and populating it
     * properties obtained from the property file using the method
     * {@link DBManager#setUpDataSource()}
     *
     * @return connection object
     */
    public BasicDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            setUpDataSource();
        }
        return dataSource;
    }

    /**
     * The method of establishing a database connection through reading and
     * setting the properties that are in the property file
     */
    public void setUpDataSource() {
        Properties prop = PropertiesUtil.getProperty(PROPERTIES_FILENAME);
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setUsername(prop.getProperty("db.user"));
        dataSource.setPassword(prop.getProperty("db.password"));
    }

    /**
     * Method for getting connection from database connection pool
     *
     * @return available connection
     */
    public Connection getConnection() {
        Connection connection;
        try {
            if (dataSource == null) {
                dataSource = getDataSource();
            }
            connection = dataSource.getConnection();
        } catch (SQLException exception) {
            throw new DataProcessingException(
                    String.format("Error receiving connection from data " +
                            "source(%s)", dataSource), exception);
        }
        LOG.debug(String.format("Connection(%s) successfully received",
                connection));

        return connection;
    }
}