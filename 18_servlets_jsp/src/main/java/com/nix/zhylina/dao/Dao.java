package com.nix.zhylina.dao;

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
     */
    void create(E entity);

    /**
     * Method that should update the data in the tuple
     *
     * @param entity the object that contains the data to update
     */
    void update(E entity);

    /**
     * Method that should update the data in the tuple
     *
     * @param entity the object that contains the data to update
     */
    void remove(E entity);

    /**
     * This method should read all tuples from the database and return them
     *
     * @return is required to return a list of read objects from the database
     */
    List<E> findAll();

    /**
     * Find and return an object that has an ID equal to {@code id}
     *
     * @param id entry number
     * @return object to be read from the database by id
     */
    E findById(Long id);
}