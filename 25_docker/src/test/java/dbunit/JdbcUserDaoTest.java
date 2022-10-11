package dbunit;

import com.nix.zhylina.database.DBManager;
import com.nix.zhylina.database.InitializeDatabase;
import com.nix.zhylina.entities.User;
import com.nix.zhylina.jdbc.JdbcUserDao;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;

import static dbunit.Constants.CONNECTION_URL;
import static dbunit.Constants.DRIVER;
import static dbunit.Constants.PASSWORD;
import static dbunit.Constants.SQL_ADD_USER;
import static dbunit.Constants.USERNAME;
import static dbunit.Constants.USER_GETTING_THE_LAST_ID;

public class JdbcUserDaoTest extends DataSourceBasedDBTestCase {
    private JdbcUserDao userDao = new JdbcUserDao();
    DBManager dbManager = new DBManager();
    private final IDatabaseTester iDatabaseTester =
            new JdbcDatabaseTester(DRIVER, CONNECTION_URL, USERNAME, PASSWORD);

    public JdbcUserDaoTest() throws Exception {
    }

    @Override
    protected DataSource getDataSource() {
        BasicDataSource ds = dbManager.getDataSource();
        ds.setUrl(CONNECTION_URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        initializeDatabase.generateTables();
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader().getResourceAsStream("dbunit/ActualDatasetUser.xml"));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Before
    public void init() throws Exception {
        getDataSource().getConnection();
        iDatabaseTester.setDataSet(getDataSet());
        iDatabaseTester.onSetup();
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setLogin("User1");
        user.setPassword("123451");
        user.setEmail("email3@gmail.com1");
        user.setFirstName("FirstName31");
        user.setLastName("LastName31");
        user.setBirthday(new Date(new GregorianCalendar(2015, 0, 01).getTimeInMillis()));
        user.setIdRole(3L);
        Connection conn = getDataSource().getConnection();
        Statement st = conn.createStatement();
        ResultSet preparedStatement = st.executeQuery(USER_GETTING_THE_LAST_ID);
        while (preparedStatement.next()) {
            user.setId(Long.parseLong(((preparedStatement.getString(1)))) + 1);
        }
        PreparedStatement statement = conn.prepareStatement(SQL_ADD_USER);
        statement.setLong(1, user.getId());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getFirstName());
        statement.setString(6, user.getLastName());
        statement.setDate(7, user.getBirthday());
        statement.setLong(8, user.getIdRole());
        statement.executeUpdate();
        IDataSet databaseDataSet = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("USERDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/CreateUser.xml"));
        ITable expectedTable = expectedDataSet.getTable("USERDAO");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdate() throws Exception {
        User testCreate = new User(3L, "User1", "123451", "email3@gmail.com1"
                , "FirstName31", "LastName31",
                new Date(new GregorianCalendar(2015, 0, 1).getTimeInMillis())
                , 2L);
        userDao.update(testCreate);
        IDataSet databaseDataSet = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("USERDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/UpdateUser.xml"));
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testRemove() throws Exception {
        User testCreate = new User("User", "12345", "email3@gmail.com",
                "FirstName3", "LastName3",
                new Date(new GregorianCalendar(2015, 0, 1).getTimeInMillis())
                , 3L);
        userDao.remove(testCreate);
        IDataSet databaseDataSet =
                iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("USERDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/DeleteUser.xml"));
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Object expectedRole = expectedTable.getValue(0, "LOGIN");
        Assert.assertEquals(expectedRole, userDao.findById(1L).getLogin());
    }

    @Test
    public void testFindAll() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Assert.assertEquals(expectedTable.getRowCount(),
                userDao.findAll().size());
    }

    @Test
    public void testFindByLogin() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Object expectedRole = expectedTable.getValue(2, "ID");
        Assert.assertEquals(expectedRole,
                userDao.findByLogin("User").getId().toString());
    }

    @Test
    public void testFindByEmail() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("USERDAO");
        Object expectedRole = expectedTable.getValue(0, "ID");
        Assert.assertEquals(expectedRole, userDao.findByEmail("email1@gmail" +
                ".com").getId().toString());
    }
}