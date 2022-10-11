package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;
import com.nix.zhylina.services.RoleDaoService;
import com.nix.zhylina.services.UserDaoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;
import static com.nix.zhylina.utils.FieldValidatorUtils.*;

/**
 * A class that implements the capabilities of the page for adding users to
 * database. When adding users, validation must be performed on
 * correctly entered data. You cannot add a user if all
 * required fields will not be filled
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */
@WebServlet(name = "AddUsersServlet", value = "/admin/add-user")
public class AddUsersController extends HttpServlet {
    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;
    private boolean isError = false;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
        roleDaoService = (RoleDaoService) getServletContext().getAttribute("RoleDaoService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("roleList", roleDaoService.findAll());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/add-users.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = getUserData(request);
            if (!isError
                    && isLoginValid(user.getLogin())
                    && isPasswordValid(user.getPassword())
                    && isEmailValid(user.getEmail())
                    && isNameValid(user.getFirstName())
                    && isNameValid(user.getLastName())
                    && isDateValid(user.getBirthday().toString())) {
                userDaoService.create(user);
                response.sendRedirect("/admin");
            } else {
                isError = false;
                response.sendRedirect("/admin/add-user");
            }
        } catch (Exception exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }

    private User getUserData(HttpServletRequest request) {
        User user = new User();
        ifLoginExists(user, request);
        user.setPassword(request.getParameter("password"));
        ifEmailExists(user, request);
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setBirthday(Date.valueOf(request.getParameter("birthday")));
        user.setIdRole(Long.valueOf((request.getParameter("idRole"))));

        return user;
    }

    public void ifEmailExists(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email").trim();

        if (userDaoService.findByEmail(email) == null) {
            user.setEmail(email);
        } else {
            isError = true;
            session.setAttribute("ifEmailExistsMessage", "User with this email already exists!");
        }
    }

    public void ifLoginExists(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login").trim();

        if (userDaoService.findByLogin(login) == null) {
            user.setLogin((login));
        } else {
            isError = true;
            session.setAttribute("ifLoginExistsMessage", "User with this login already exists!");
        }
    }
}