package com.nix.zhylina.rest;

import com.nix.zhylina.dto.AuthDto;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.exceptions.ApiError;
import com.nix.zhylina.exceptions.CustomErrorHandling;
import com.nix.zhylina.jwt.JwtTokenProvider;
import com.nix.zhylina.services.UserService;
import com.nix.zhylina.utils.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.Constants.CONST_ERRORS;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody AuthDto requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            Optional <UserEntity> userForLogin = userService.findByUsername(username);

            if (!userForLogin.isPresent()) {
                    throw new UsernameNotFoundException(AuthConstants.CONST_PREFIX_USERNAME_NOT_FOUND + username + AuthConstants.CONST_ENDING_USERNAME_NOT_FOUND);
            }

            String token = jwtTokenProvider.createToken(username, userForLogin.get().getRoleEntity().getName());
            Map<Object, Object> response = Map.of(AuthConstants.CONST_VALUE_USERNAME, username, AuthConstants.CONST_VALUE_TOKEN_LOWERCASE, token,
                    "user", userForLogin.get());

            return Response.ok(response).build();
        } catch (AuthenticationException e) {
            Map<String, String> errorList = Map.of(CONST_ERROR, AuthConstants.ERROR_VALUE_INCORRECT_LOGIN_OR_PASSWORD);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorList));
        }
    }
}