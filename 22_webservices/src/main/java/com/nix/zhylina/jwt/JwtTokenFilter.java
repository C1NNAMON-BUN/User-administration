package com.nix.zhylina.jwt;

import com.nix.zhylina.exceptions.ApiError;
import com.nix.zhylina.exceptions.CustomErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.nix.zhylina.utils.Constants.CONST_ERRORS;
import static com.nix.zhylina.utils.Constants.ERROR_TOKEN;

public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationCredentialsNotFoundException | BadCredentialsException e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            HashMap<String, String> errorsList = new HashMap<>();

            errorsList.put(ERROR_TOKEN, e.getMessage());
            response.getWriter().write(CustomErrorHandling.jsonObject(new ApiError(HttpStatus.UNAUTHORIZED, CONST_ERRORS, errorsList)));
        }
    }
}
