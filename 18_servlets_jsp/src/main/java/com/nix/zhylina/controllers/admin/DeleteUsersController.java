package com.nix.zhylina.controllers.admin;

import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.services.UserDaoService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;

/**
 * A class that implements the function of deleting users from the database
 * on condition. The administrator cannot delete himself, but he can delete
 * other users and administrators.
 * Before deleting a user, you must display a message with a question
 * is he sure he wants to delete, if yes, continue
 * deletion, otherwise cancel.
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 17.02.2022 </p>
 */
@WebServlet(name = "DeleteUsersServlet", value = "/delete-user")
public class DeleteUsersController extends HttpServlet {
    private UserDaoService userDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("deleteButton") != null) {
                userDaoService.remove(userDaoService.findByLogin(String.valueOf(request.getParameter("deleteButton"))));
                response.sendRedirect("/admin");
            }
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}
