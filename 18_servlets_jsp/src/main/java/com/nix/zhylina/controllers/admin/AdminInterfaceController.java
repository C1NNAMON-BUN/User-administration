package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;
import com.nix.zhylina.dao.impl.JdbcUserDao;
import com.nix.zhylina.services.UserDaoService;
import com.nix.zhylina.tag.UserListTag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;

/**
 * A class that implements the features of the main admin page. On the
 * this page displays a list of all users using
 * {@link UserListTag} and the removal functions from
 * using {@link DeleteUsersController} and method
 * {@link JdbcUserDao#remove(User)}, as well as editing users
 * {@link EditUsersController} and the {@link JdbcUserDao#update(User)} method. BUT
 * also the ability to go to the page for creating a new user with
 * using {@link AddUsersController} and method
 * {@link JdbcUserDao#create(User)}.
 * The page also has the ability to log out of your account
 * {@link com.nix.zhylina.controllers.LogOutController}
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */
@WebServlet(name = "AdminInterfaceServlet", value = "/admin")
public class AdminInterfaceController extends HttpServlet {
    private UserDaoService userDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> userArrayList = userDaoService.findAll();

        try {
            request.setAttribute("users", userArrayList);
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/views/admin-page.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}