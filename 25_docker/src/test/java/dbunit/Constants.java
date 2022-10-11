package dbunit;

public class Constants {
    public static final String DRIVER = "org.h2.Driver";
    public static final String CONNECTION_URL = "jdbc:h2:mem:testDB";
    public static final String USERNAME = "test";
    public static final String PASSWORD = "test";

    public static final String ROLE_GETTING_THE_LAST_ID = "SELECT id FROM " +
            "ROLEDAO ORDER BY id DESC LIMIT 1";
    public static final String USER_GETTING_THE_LAST_ID = "SELECT id FROM " +
            "USERDAO ORDER BY id DESC LIMIT 1";
    public static final String SQL_ADD_USER = "INSERT INTO USERDAO " +
            "VALUES (?,?,?,?,?,?,?,?);";
    public static final String SQL_ADD_ROLE = "INSERT INTO ROLEDAO " +
            "VALUES (?,?)";
}
