package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;
import com.nix.zhylina.services.RoleDaoService;
import com.nix.zhylina.services.UserDaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;
import static com.nix.zhylina.utils.FieldValidatorUtils.isDateValid;
import static com.nix.zhylina.utils.FieldValidatorUtils.isEmailValid;
import static com.nix.zhylina.utils.FieldValidatorUtils.isNameValid;
import static com.nix.zhylina.utils.FieldValidatorUtils.isPasswordValid;

/**
 * A class that implements the user editing function. During
 * its display must pull up all user data, but hide
 * password.
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */
@WebServlet(name = "EditUsersServlet", value = "/admin/edit-user")
public class EditUsersController extends HttpServlet {
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
            User user = userDaoService.findByLogin(request.getParameter("login"));
            request.setAttribute("login", user.getLogin());
            request.setAttribute("password", user.getPassword());
            request.setAttribute("confirmPassword", user.getPassword());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("birthday", user.getBirthday());
            request.setAttribute("idRole", user.getIdRole());

            request.getRequestDispatcher("/WEB-INF/views/edit-users.jsp").forward(request, response);
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
            if (!isError & isPasswordValid(user.getPassword())
                    && isEmailValid(user.getEmail())
                    && isNameValid(user.getFirstName())
                    && isNameValid(user.getLastName())
                    && isDateValid(user.getBirthday().toString())) {
                userDaoService.update(user);
                response.sendRedirect("/admin");

            } else {
                isError = false;
                doGet(request, response);
            }
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }

    private User getUserData(HttpServletRequest request) {
        User user = userDaoService.findByLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        ifEmailExists(user, request);
        user.setFirstName((request.getParameter("firstName")));
        user.setLastName((request.getParameter("lastName")));
        user.setBirthday(Date.valueOf(request.getParameter("birthday")));
        user.setIdRole(Long.valueOf((request.getParameter("idRole"))));

        return user;
    }

    public void ifEmailExists(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");

        if (!user.getEmail().equals(email)) {
            if (userDaoService.findByEmail(email) == null) {
                user.setEmail(email);
            } else {
                isError = true;
                session.setAttribute("ifEmailExistsMessage", "User with this email already exists!");
            }
        }
    }
}
