package com.nix.zhylina.tag;

import com.nix.zhylina.entitie.User;
import com.nix.zhylina.dao.impl.HibernateRoleDao;
import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.exception.NullFieldValueException;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class in which the custom tag function is implemented, the function of
 * which is to initialize a table with users from the database
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 17.02.2022
 */
@Getter
@Setter
public class UserListTag extends SimpleTagSupport {
    private List<User> users = new ArrayList<>();
    private JspWriter out;
    private String adminName;
    private HibernateUserDao hibernateUserDao = new HibernateUserDao();
    private HibernateRoleDao hibernateRoleDao = new HibernateRoleDao();

    void init() {
        final PageContext pageContext = (PageContext) getJspContext();
        final HttpSession session = pageContext.getSession();
        adminName = (String) session.getAttribute("name");
        out = pageContext.getOut();
    }

    @Override
    public void doTag() throws IOException {
        init();
        try {
            for (User user : users) {
                String login = user.getLogin();
                out.write("<td>" + user.getLogin() + "</td>");
                out.write("<td>" + user.getFirstName() + "</td>");
                out.write("<td>" + user.getLastName() + "</td>");
                out.write("<td>" + user.getUserAge() + "</td>");
                out.write("<td>" + hibernateRoleDao.findById(hibernateUserDao.findByLogin(login).getRole().getId()).getName() + "</td>");
                out.write("<td>" +
                        "<form action=\"/admin/edit-user\" method=\"get\">" +
                        "<button type=\"submit\" class=\"ghost-round " +
                        "full-width\">edit</button>" +
                        "<input type=\"hidden\" name=\"login\" value=\"" + user.getLogin() + "\"/>" +
                        "</form>" +
                        "<form action=\"/delete-user\" method=\"post\">\n" +
                        "<button name=\"deleteButton\"  class=\"ghost-round " +
                        "full-width\" value=\"" + user.getLogin() + "\" " +
                        "class=\"btn btn-info\"\n");
                if (adminName.equals(user.getFirstName())) {
                    out.write(" disabled>");
                } else {
                    out.write(" onclick=\"return confirm('Are you sure?')\">");
                }
                out.write(" Delete\n" +
                        "</button>\n" +
                        "</form>\n" +
                        "</td>\n" +
                        "</tr>\n");
            }
            out.close();
        } catch (NullFieldValueException e) {
            out.write("<script type=\"text/javascript\">\n" +
                    "alert(\"An error occurred while loading the user list!\");\n" +
                    "</script>");
        }
    }
}
