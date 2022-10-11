package com.nix.zhylina.exception;

/**
 * <p><h2>RoleDataProcessingException</h2>
 * <b>This exception is used in case of an error
 * {@link java.sql.SQLException} in a class
 * {@link com.nix.zhylina.dao.impl.JdbcRoleDao},
 * </b> <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class RoleDataProcessingException extends DataProcessingException {
    public RoleDataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}