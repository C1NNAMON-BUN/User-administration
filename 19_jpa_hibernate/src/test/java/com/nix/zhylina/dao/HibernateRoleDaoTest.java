package com.nix.zhylina.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;

import com.nix.zhylina.dao.impl.HibernateRoleDao;
import com.nix.zhylina.entitie.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
@DataSet(value = "dbunit/ActualDatasetUser.xml",
        executeScriptsBefore = "dbunit/init_tables.sql",
        executeScriptsAfter = "dbunit/drop_tables.sql")
public class HibernateRoleDaoTest {

    private HibernateRoleDao roleDao;
    private Role expectedRole;

    @Before
    public void beforeTest() {
        roleDao = new HibernateRoleDao();
        expectedRole = new Role();
    }

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    @Test
    public void shouldCreateRole() {
        expectedRole.setName("admin4");
        roleDao.create(expectedRole);
        Assert.assertEquals("admin4",roleDao.findByName("admin4").getName());
    }

    @Test
    @ExpectedDataSet(value = "dbunit/UpdateRole.xml")
    public void shouldUpdateRole() {
        expectedRole.setId(1L);
        expectedRole.setName("updatedRole");
        roleDao.update(expectedRole);
    }

    @Test
    @ExpectedDataSet(value = "dbunit/DeleteRole.xml")
    public void shouldRemoveRole() {
        expectedRole.setId(1L);
        expectedRole.setName("Admin");
        roleDao.remove(expectedRole);

    }

    @Test
    public void shouldFindAllRoles()  {
        List<Role> expectedListWithRoles = Arrays.asList(new Role(1L, "Admin"),new Role(2L,"User"));
        assertEquals(expectedListWithRoles, roleDao.findAll());
    }

    @Test
    public void shouldFindRoleById()  {
        expectedRole.setId(2L);
        expectedRole.setName("User");
        Assert.assertEquals(expectedRole, roleDao.findById(2L));
    }

    @Test
    public void shouldFindRoleByName()  {
        expectedRole.setId(1L);
        expectedRole.setName("Admin");
        assertEquals(expectedRole, roleDao.findByName("Admin"));
    }
}