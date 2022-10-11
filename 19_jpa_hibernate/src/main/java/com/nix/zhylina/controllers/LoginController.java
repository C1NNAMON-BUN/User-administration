package com.nix.zhylina.controllers;

import com.nix.zhylina.entitie.User;
import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;
import com.nix.zhylina.services.UserDaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.nix.zhylina.constants.Constants.ADMIN_ROLE_ID;
import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;
import static com.nix.zhylina.constants.Constants.USER_ROLE_ID;

/**
 * A class that implements the ability to enter the site if the user
 * is in the database. Depending on the access level, the user will
 * sent to admin page
 * {@link com.nix.zhylina.controllers.admin.AdminInterfaceController} or
 * {@link com.nix.zhylina.controllers.user.UserInterfaceController}
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.04.2022
 */
@WebServlet(name = "HomeServlet", value = "/login")
public class LoginController extends HttpServlet {
    private UserDaoService userDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean currentUser = userDaoService.isUserExists(login, password);
        HttpSession session = request.getSession();

        if (currentUser) {
            User user = userDaoService.findByLogin(login);
            Long userRole = user.getRole().getId();
            session.setAttribute("name", user.getFirstName());
            session.setAttribute("idRole", userRole);
            siteNavigation(response, userRole);
        } else {
            try {
                session.setAttribute("message", "Incorrect login or password!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (ServletException exception) {
                throw new ServletProcessingException(ERROR_SERVLET, exception);
            } catch (IOException exception) {
                throw new ServletProcessingException(ERROR_PAGE, exception);
            }
        }
    }

    public void siteNavigation(HttpServletResponse response, Long userRole) {
        try {
            if (userRole.equals(ADMIN_ROLE_ID)) {
                response.sendRedirect("/admin");
            } else if (userRole.equals(USER_ROLE_ID)) {
                response.sendRedirect("/user");
            } else {
                response.sendRedirect("/login");
            }
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}


