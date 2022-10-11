package com.nix.zhylina.dao.impl;

import com.nix.zhylina.dao.Dao;
import com.nix.zhylina.database.DBManager;
import com.nix.zhylina.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p><h2>GenericJdbcDao</h2>
 * <b>This class is responsible for database connections and should contain
 * imagine repetitions that are in
 * {@link com.nix.zhylina.dao.impl.JdbcRoleDao} and
 * {@link com.nix.zhylina.dao.impl.JdbcUserDao}</b><br>
 *
 * @param <E> when extending the implementation of a class, you must specify
 *            the type that the class will work with
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public abstract class GenericJdbcDao<E> implements Dao<E> {
    private final DBManager dbManager = new DBManager();
    public Connection connection = null;
    public PreparedStatement preparedStatement = null;

    /**
     * This method connects to a connection pool, accepts
     * self-request and turn off automatic transaction management
     *
     * @param sql SQL query to be prepared and
     *            formatted with {@link PreparedStatement}
     * @return must return the accepted request
     */
    public PreparedStatement executeStatement(String sql) {
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
        } catch (SQLException sqlException) {
            throw new DataProcessingException("Connection error!", sqlException);
        }
        return preparedStatement;
    }

    /**
     * The method in which the request is executed, the transaction is
     * committed, its return if method fails
     * {@link GenericJdbcDao#connectionRollback() and close the connection with
     * using the {@link GenericJdbcDao#close()} method
     */
    public void closeUpdate() {
        if (preparedStatement != null) {
            try {
                preparedStatement.executeUpdate();
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
            } catch (SQLException sqlException) {
                connectionRollback();
                throw new DataProcessingException("Request execution error!",
                        sqlException);
            } finally {
                close();
            }
        }
    }

    /**
     * Method in which the transaction is rolled back in case of a query
     * execution error
     */
    public void connectionRollback() {
        try {
            connection.rollback();
        } catch (SQLException sqlException) {
            throw new DataProcessingException("Transaction rollback error!",
                    sqlException);
        }
    }

    /**
     * The method in which connections are closed
     */
    public void close() {
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException sqlException) {
            throw new DataProcessingException("Error closing connections!",
                    sqlException);
        }
    }

    /**
     * Manual closing of the read data from the database
     *
     * @param resultSet the result set to close
     */
    public void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new DataProcessingException("Error closing result set!",
                    sqlException);
        }
    }
}