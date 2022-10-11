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

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;

/**
 * A class that implements the user editing function. During
 * its display must pull up all user data, but hide
 * password.
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 08.03.2022 </p>
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
        HttpSession session = request.getSession();

        try {
            request.setAttribute("roleList", roleDaoService.findAll());
            User user = userDaoService.findByLogin(request.getParameter("login"));
            Role role = roleDaoService.findById(user.getRole().getId());
            request.setAttribute("login", user.getLogin());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("password", user.getPassword());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("birthday", user.getBirthday());
            request.setAttribute("idRole", role.getName());
            request.getRequestDispatcher("/WEB-INF/views/edit-users.jsp").forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (NullFieldValueException e) {
            session.setAttribute("errorMessageDuringObjectCreation", "Sorry, an error occurred while loading the data!");
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
            user = getUserData(request, response);
            if (!isError
                    && userDaoService.isUserDataValid(user)
                    && user.getPassword().equals(request.getParameter("confirmPassword"))) {
                userDaoService.update(user);
                backToAdminPage(response);
            } else {
                isError = false;
                doGet(request, response);
            }
        } catch (NullPointerException exception) {
            session.setAttribute("errorMessageDuringObjectCreation", "An error occurred while creating the entry!");
            doGet(request, response);
        }
    }

    private User getUserData(HttpServletRequest request, HttpServletResponse response) throws NullPointerException {
        User user = new User();
        HttpSession session = request.getSession();

        try {
            user = userDaoService.findByLogin(request.getParameter("login"));
            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setBirthday(request.getParameter("birthday").isEmpty() ?
                    null : LocalDate.parse(request.getParameter("birthday")));
            user.setRole(roleDaoService.findById(Long.valueOf(request.getParameter("idRole"))));
            ifEmailExists(user, request);

        } catch (NullFieldValueException e) {
            session.setAttribute("dataError", "Sorry, an error occurred while creating the user!");
            backToAdminPage(response);
        }
        return user;
    }

    public void ifEmailExists(User user, HttpServletRequest request) throws NullPointerException {
        HttpSession session = request.getSession();
        String email = "";

        try {
            email = request.getParameter("email");
            if (userDaoService.findByEmail(email) != null) {
                if (!user.getEmail().equals(email)) {
                    isError = true;
                    session.setAttribute("ifEmailExistsMessage", "User with this email already exists!");
                }
            }
        } catch (NullFieldValueException e) {
            if (!user.getEmail().equals(email)) {
                user.setEmail((email));
            }
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