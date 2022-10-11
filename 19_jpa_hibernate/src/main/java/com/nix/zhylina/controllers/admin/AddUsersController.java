package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.entitie.Role;
import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.NullFieldValueException;
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
import java.time.LocalDate;
import java.util.List;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;

/**
 * A class that implements the capabilities of the page for adding users to
 * database. When adding users, validation must be performed on
 * correctly entered data. You cannot add a user if all
 * required fields will not be filled
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 08.03.2022 </p>
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
        HttpSession session = request.getSession();

        try {
            List<Role> roleList = roleDaoService.findAll();
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("/WEB-INF/views/add-users.jsp")
                    .forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (NullFieldValueException e) {
            session.setAttribute("errorMessageDuringObjectCreation", "Sorry, no roles found!");
            backToAdminPage(response);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user;
        HttpSession session = request.getSession();

        try {
            user = addingUserToTheDatabase(request);
            if (!isError
                    && userDaoService.isUserDataValid(user)
                    && user.getPassword().equals(request.getParameter("confirmPassword"))) {
                userDaoService.create(user);
                response.sendRedirect("/admin");
            } else {
                isError = false;
                response.sendRedirect("/admin/add-user");
            }
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        } catch (NullPointerException exception) {
            session.setAttribute("errorMessageDuringObjectCreation", "An error occurred while creating the entry!");
            doGet(request, response);
        }
    }

    public User addingUserToTheDatabase(HttpServletRequest request) throws NullPointerException {
        User user = new User();
        ifLoginExists(user, request);
        user.setRole(roleDaoService.findById(Long.valueOf(request.getParameter("idRole"))));
        user.setPassword(request.getParameter("password").trim());
        user.setFirstName(request.getParameter("firstName").trim());
        user.setLastName(request.getParameter("lastName").trim());
        user.setBirthday(LocalDate.parse(request.getParameter("birthday").trim()));
        ifEmailExists(user, request);

        return user;
    }

    public void ifEmailExists(User user, HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession();
        String email = "";

        try {
            email = request.getParameter("email").trim();
            if (userDaoService.findByEmail(email) != null) {
                isError = true;
                session.setAttribute("ifEmailExistsMessage", "User with this email already exists!");
            }
        } catch (NullFieldValueException e) {
            user.setEmail((email));
        }
    }

    public void ifLoginExists(User user, HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession();
        String login = "";

        try {
            login = request.getParameter("login").trim();
            if (userDaoService.findByLogin(login) != null) {
                isError = true;
                session.setAttribute("ifLoginExistsMessage", "User with this login already exists!");
            }
        } catch (NullFieldValueException e) {
            user.setLogin((login));
        }
    }

    public void backToAdminPage(HttpServletResponse response) {
        try {
            response.sendRedirect("/admin");
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}