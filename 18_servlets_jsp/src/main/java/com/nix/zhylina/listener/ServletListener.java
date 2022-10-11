package com.nix.zhylina.listener;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.dao.impl.JdbcRoleDao;
import com.nix.zhylina.dao.impl.JdbcUserDao;
import com.nix.zhylina.services.RoleDaoService;
import com.nix.zhylina.services.UserDaoService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class that initializes services that perform an operation on the database {@link RoleDaoService} and
 * {@link UserDaoService}
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 22.02.2022
 */
@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        ServletContext context = contextEvent.getServletContext();
        UserDao userDao = new JdbcUserDao();
        RoleDao roleDao = new JdbcRoleDao();

        UserDaoService userDaoService = new UserDaoService(userDao);
        RoleDaoService roleDaoService = new RoleDaoService(roleDao);

        context.setAttribute("UserDaoService", userDaoService);
        context.setAttribute("RoleDaoService", roleDaoService);
    }
}