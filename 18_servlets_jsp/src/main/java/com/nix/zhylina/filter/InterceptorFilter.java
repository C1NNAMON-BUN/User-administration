package com.nix.zhylina.filter;

import com.nix.zhylina.exception.DataProcessingException;
import com.nix.zhylina.exception.ServletProcessingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.nix.zhylina.constants.Constants.ERROR_PAGE;
import static com.nix.zhylina.constants.Constants.ERROR_SERVLET;
import static com.nix.zhylina.constants.Constants.USER_ROLE_ID;

/**
 * A class that implements the filter-interceptor function. This filter
 * does not allow the user to go to pages that do not match
 * his access rights. In case of unauthorized access, the user
 * will be redirected to the registration page.
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */

public class InterceptorFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) {
        try {
            request.setCharacterEncoding("UTF-8");
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession session = ((HttpServletRequest) request).getSession();
            String uri = httpServletRequest.getRequestURI();

            if (session.getAttribute("idRole") == null
                    || session.getAttribute("idRole").equals(USER_ROLE_ID) & !uri.equals("/user")) {
                httpServletRequest.getRequestDispatcher("/login")
                        .forward(httpServletRequest, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (ServletException exception) {
            throw new ServletProcessingException(ERROR_SERVLET, exception);
        } catch (IOException exception) {
            throw new DataProcessingException(ERROR_PAGE, exception);
        }
    }
}
