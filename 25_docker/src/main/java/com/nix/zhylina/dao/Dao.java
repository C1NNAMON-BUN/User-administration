package com.nix.zhylina.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * <p><h2>Dao</h2>
 * <b>An interface that contains common methods and operations on tables</b>
 * <br>
 *
 * @param <E> when extending an interface implementation, you must specify
 *            the type class to be accepted
 * @author Zhilina Svetlana
 * @version1.0
 * @since 26.01.2022 </p>
 */
public interface Dao<E> {
    /**
     * The method that should do the creation of the tuple in the database
     *
     * @param entity the object that will be written to the database. Object
     *               ID must not be specified
     * @throws SQLException throw own exception on error
     */
    void create(E entity) throws SQLException;

    /**
     * Method that should update the data in the tuple
     *
     * @param entity the object that contains the data to update
     * @throws SQLException throw own exception on error
     */
    void update(E entity) throws SQLException;

    /**
     * Method that should update the data in the tuple
     *
     * @param entity the object that contains the data to update
     * @throws SQLException throw own exception on error
     */
    void remove(E entity) throws SQLException;

    /**
     * This method should read all tuples from the database and return them
     *
     * @return is required to return a list of read objects from the database
     * @throws SQLException throw own exception on error
     */
    List<E> findAll() throws SQLException;

    /**
     * Find and return an object that has an ID equal to {@code id}
     *
     * @param id entry number
     * @return object to be read from the database by id
     * @throws SQLException throw own exception on error
     */
    E findById(Long id) throws SQLException;
}