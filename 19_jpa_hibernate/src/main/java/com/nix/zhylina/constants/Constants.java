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
 * @since 08.03.2022 </p>
 */
public class Constants {
    /**
     * Constants that contain error messages in case of execution
     */
    public static final String ERROR_PAGE = "There was a page transition error!";
    public static final String ERROR_SERVLET = "Error while executing servlet!";
    public static final String SEARCH_ERROR = "An error occurred while searching";

    /**
     * Constants with role id values
     */
    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long USER_ROLE_ID = 2L;
}
