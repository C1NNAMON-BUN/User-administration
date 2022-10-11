package dbunit;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.nix.zhylina.database.DBManager;
import com.nix.zhylina.database.InitializeDatabase;
import com.nix.zhylina.entities.Role;
import com.nix.zhylina.jdbc.JdbcRoleDao;

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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static dbunit.Constants.CONNECTION_URL;
import static dbunit.Constants.DRIVER;
import static dbunit.Constants.PASSWORD;
import static dbunit.Constants.ROLE_GETTING_THE_LAST_ID;
import static dbunit.Constants.SQL_ADD_ROLE;
import static dbunit.Constants.USERNAME;

@DBUnit
public class JdbcRoleDaoTest extends DataSourceBasedDBTestCase {
    private final JdbcRoleDao roleDao = new JdbcRoleDao();
    DBManager dbManager = new DBManager();
    private final IDatabaseTester iDatabaseTester =
            new JdbcDatabaseTester(DRIVER, CONNECTION_URL, USERNAME, PASSWORD);


    public JdbcRoleDaoTest() throws Exception {
    }

    @Override
    protected DataSource getDataSource() {
        BasicDataSource dataSource = dbManager.getDataSource();
        dataSource.setUrl(CONNECTION_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
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
                .build(getClass().getClassLoader().getResourceAsStream(
                        "dbunit/ActualDatasetRole.xml"));
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
        Role role = new Role("NewRole");
        Connection conn = getDataSource().getConnection();
        Statement st = conn.createStatement();
        ResultSet preparedStatement = st.executeQuery(ROLE_GETTING_THE_LAST_ID);
        while (preparedStatement.next()) {
            role.setId(Long.parseLong(((preparedStatement.getString(1)))) + 1);
        }
        PreparedStatement statement = conn.prepareStatement(SQL_ADD_ROLE);
        statement.setLong(1, role.getId());
        statement.setString(2, role.getName());
        statement.executeUpdate();
        IDataSet databaseDataSet = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("ROLEDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/CreateRole.xml"));
        ITable expectedTable = expectedDataSet.getTable("ROLEDAO");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdate() throws Exception {
        Role testCreate = new Role(2L, "Moderator");
        roleDao.update(testCreate);
        IDataSet databaseDataSet = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("ROLEDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/UpdateRole.xml"));
        ITable expectedTable = expectedDataSet.getTable("ROLEDAO");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testRemove() throws Exception {
        Role testCreate = new Role("User");
        roleDao.remove(testCreate);
        IDataSet databaseDataSet = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("ROLEDAO");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("dbunit/DeleteRole.xml"));
        ITable expectedTable = expectedDataSet.getTable("ROLEDAO");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("ROLEDAO");
        Object expectedRole = expectedTable.getValue(0, "NAME");
        Assert.assertEquals(expectedRole, roleDao.findById(1L).getName());
    }

    @Test
    public void testFindByName() throws Exception {
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("ROLEDAO");
        Object expectedRole = expectedTable.getValue(0, "Name");
        Assert.assertEquals(expectedRole, roleDao.findByName("Admin1").getName());
    }
}
