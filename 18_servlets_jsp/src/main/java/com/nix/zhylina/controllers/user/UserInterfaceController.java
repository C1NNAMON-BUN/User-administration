package com.nix.zhylina.controllers.user;

import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;

/**
 * A class that implements the user greeting function. Page
 * is the main page for the user and displays his name, also
 * contains the ability to log out of the account using
 * {@link com.nix.zhylina.controllers.LogOutController}
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 17.02.2022 </p>
 */
@WebServlet(name = "UserInterfaceServlet", value = "/user")
public class UserInterfaceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user-page.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}