package com.nix.zhylina.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;

import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.entitie.Role;
import com.nix.zhylina.entitie.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
@DataSet(value = "dbunit/ActualDatasetUser.xml",
        executeScriptsBefore = "dbunit/init_tables.sql",
        executeScriptsAfter = "dbunit/drop_tables.sql")
public class HibernateUserDaoTest {

    private HibernateUserDao userDao;
    private User expectedUser;

    @Before
    public void beforeTest() {
        userDao = new HibernateUserDao();
        expectedUser = new User();
    }

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    @Test
     public void createUserTest() {
        Role role = new Role();
        role.setName("NewRole");
        role.setId(2L);
        expectedUser.setLogin("NewUser");
        expectedUser.setPassword("asdfasdf");
        expectedUser.setEmail("email3@gmail.com");
        expectedUser.setFirstName("FirstName");
        expectedUser.setLastName("LastName");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);
        userDao.create(expectedUser);
        Assert.assertEquals("email3@gmail.com",userDao.findByLogin("NewUser").getEmail());
    }

    @Test
    @ExpectedDataSet(value = "dbunit/UpdateUser.xml")
    public void updateUserTest() {
        Role role = new Role();
        role.setName("NewRole");
        role.setId(3L);

        expectedUser.setId(2L);
        expectedUser.setLogin("NewUser");
        expectedUser.setPassword("asdfasdf");
        expectedUser.setEmail("email3@gmail.com");
        expectedUser.setFirstName("FirstNameUpd");
        expectedUser.setLastName("LastNameUpd");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);
        userDao.update(expectedUser);
    }

    @Test
    @ExpectedDataSet(value = "dbunit/DeleteUser.xml")
    public void removeUserTest() {
        Role role = new Role();
        role.setName("Admin");
        role.setId(1L);

        expectedUser.setId(2L);
        expectedUser.setLogin("User");
        expectedUser.setPassword("543");
        expectedUser.setEmail("email2@gmail.com");
        expectedUser.setFirstName("FirstName2");
        expectedUser.setLastName("LastName2");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);
        userDao.remove(userDao.findByLogin("User"));
    }

    @Test
    public void findAllUsersTest()  {
        assertEquals(2, userDao.findAll().size());
    }

    @Test
    public void findUserByIdTest()  {
        Role role = new Role();
        role.setName("User");
        role.setId(2L);

        expectedUser.setId(2L);
        expectedUser.setLogin("User");
        expectedUser.setPassword("543");
        expectedUser.setEmail("email2@gmail.com");
        expectedUser.setFirstName("FirstName2");
        expectedUser.setLastName("LastName2");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);

        assertEquals(expectedUser, userDao.findById(2L));
    }

    @Test
    public void findUserByLoginTest()  {
        Role role = new Role();
        role.setName("User");
        role.setId(2L);

        expectedUser.setId(2L);
        expectedUser.setLogin("User");
        expectedUser.setPassword("543");
        expectedUser.setEmail("email2@gmail.com");
        expectedUser.setFirstName("FirstName2");
        expectedUser.setLastName("LastName2");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);

        Assert.assertEquals(expectedUser, userDao.findByLogin("User"));
    }

    @Test
    public void findUserByEmailTest()  {
        Role role = new Role();
        role.setName("User");
        role.setId(2L);

        expectedUser.setId(2L);
        expectedUser.setLogin("User");
        expectedUser.setPassword("543");
        expectedUser.setEmail("email2@gmail.com");
        expectedUser.setFirstName("FirstName2");
        expectedUser.setLastName("LastName2");
        expectedUser.setBirthday(LocalDate.parse("2015-01-01"));
        expectedUser.setRole(role);

        Assert.assertEquals(expectedUser, userDao.findByEmail("email2@gmail.com"));
    }
}
