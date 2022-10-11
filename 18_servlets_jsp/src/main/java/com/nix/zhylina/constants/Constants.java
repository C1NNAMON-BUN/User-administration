package com.nix.zhylina.constants;

/**
 * <p><h2>Constants</h2>
 * <b>A class that contains constants</b> <br>
 * This class contains constants for the following purposes:
 * <ul>
 * <li>SQL queries for RoleDao and UserDao tables</li>
 * <li>Properties file name </li>
 * <li>Error messages</li>
 * <li>Path to SQL scripts</li>
 * </ul>
 *
 * @author Zhilina Svetlana
 * @version 4.0
 * @since 09.03.2022
 */
public class Constants {
    private Constants() {
    }

    /**
     * Constants that contain SQL queries for the UserDao table
     */
    public static final String SQL_ADD_USER = "INSERT INTO USERDAO (login, " +
            "password, email, firstName, lastName, birthday, id_Role) " +
            "VALUES (?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_USER = "UPDATE USERDAO SET login = ?, " +
            "password = ?,email = ?, firstName = ?, lastName = ?, " +
            "birthday = ?, id_Role = ? WHERE id = ?";
    public static final String SQL_FIND_BY_ID_USER = "SELECT * FROM USERDAO " +
            "WHERE id LIKE ?";
    public static final String SQL_REMOVE_USER = "DELETE FROM USERDAO WHERE " +
            "email = ?";
    public static final String SQL_SELECT_USER = "SELECT * FROM USERDAO";
    public static final String SQL_FIND_BY_LOGIN = "SELECT * FROM USERDAO " +
            "WHERE login LIKE ?";
    public static final String SQL_FIND_BY_EMAIL = "SELECT * FROM USERDAO " +
            "WHERE email LIKE ?";

    /**
     * Constants that contain SQL queries for the RoleDao table
     */
    public static final String SQL_ADD_ROLE = "INSERT INTO ROLEDAO (name) " +
            "VALUES (?)";
    public static final String SQL_UPDATE_ROLE = "UPDATE ROLEDAO SET name = ?" +
            " WHERE id = ?";
    public static final String SQL_DELETE_ROLE = "DELETE FROM ROLEDAO WHERE " +
            "name = ?";
    public static final String SQL_FIND_BY_NAME = "SELECT * FROM ROLEDAO " +
            "WHERE name LIKE ?";
    public static final String SQL_SELECT_ROLE = "SELECT * FROM ROLEDAO";
    public static final String SQL_FIND_BY_ID_ROLE = "SELECT * FROM ROLEDAO " +
            "WHERE id LIKE ?";

    /**
     * A constant that contains the name of the properties file, in
     * which contains the data for connecting to the database
     */
    public static final String PROPERTIES_FILENAME = "config.properties";

    /**
     * Constants that contain error messages in case of execution
     */
    public static final String ERROR_CREATION_TUPLE = "Error during tuple " +
            "creation!";
    public static final String ERROR_UPDATE_TUPLE = "Error while updating " +
            "tuple!";
    public static final String ERROR_REMOVING_TUPLE = "Error while deleting " +
            "tuple!";
    public static final String ERROR_SEARCH_TUPLE = "Error during record " +
            "search!";
    public static final String ERROR_PAGE = "There was a page transition error!";
    public static final String ERROR_SERVLET = "Error while executing servlet!";

    /**
     * Constants that contain paths to SQL scripts
     */
    public static final String DB_INITIALIZATION_SCRIPT = "db.init.script.source";

    /**
     * Constants with role id values
     */
    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long USER_ROLE_ID = 2L;
}
