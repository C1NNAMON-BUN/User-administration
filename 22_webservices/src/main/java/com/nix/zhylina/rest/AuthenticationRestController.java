package com.nix.zhylina.rest;

import com.nix.zhylina.dto.AuthUserDto;
import com.nix.zhylina.exceptions.ApiError;
import com.nix.zhylina.exceptions.CustomErrorHandling;
import com.nix.zhylina.jwt.JwtTokenProvider;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static com.nix.zhylina.utils.Constants.*;

@RestController
public class AuthenticationRestController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody AuthUserDto requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            UserEntity userForLogin = userService.findByUsername(username).orElse(null);
            Map<Object, Object> response = new HashMap<>();

            if (userForLogin == null) {
                throw new UsernameNotFoundException(CONST_PREFIX_USERNAME_NOT_FOUND + username + CONST_ENDING_USERNAME_NOT_FOUND);
            }

            String token = jwtTokenProvider.createToken(username, userForLogin.getRoleEntity().getName());

            response.put(CONST_VALUE_USERNAME, username);
            response.put(CONST_VALUE_TOKEN_LOWERCASE, token);

            return Response.ok(response).build();
        } catch (AuthenticationException e) {
            Map<String, String> errorList = new HashMap<>();

            errorList.put(CONST_ERROR, ERROR_VALUE_INCORRECT_LOGIN_OR_PASSWORD);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorList));
        }
    }
}
