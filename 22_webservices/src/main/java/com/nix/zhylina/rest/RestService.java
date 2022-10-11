package com.nix.zhylina.rest;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.exceptions.ApiError;
import com.nix.zhylina.exceptions.CustomErrorHandling;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nix.zhylina.utils.Constants.*;

@Validated
@RestController
public class RestService {
    @Autowired
    private IUserService userDao;
    @Autowired
    private Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithId(@QueryParam("id") Long id) {
        UserEntity user = userDao.findById(id).orElse(null);
        ;

        if (user != null) {
            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = new HashMap<>();

            errorList.put(CONST_ERROR, ERROR_USER_WITH_ID_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorList));
        }
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserEntity> users = userDao.findAll();

        if (!users.isEmpty()) {
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = new HashMap<>();

            errorList.put(CONST_ERROR, ERROR_USERS_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorList));
        }
    }

    @POST
    @Path("/user")
    @Validated
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@RequestBody UserEntity user) {
        Map<String, String> errorsList = validator.validateAddNewUser(user);

        if (errorsList.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(user);

            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorsList));
        }
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@RequestBody UserEntity user) {
        Map<String, String> errorsList = validator.validateUpdateUser(user);

        if (errorsList.isEmpty()) {
            if (user.getPassword().equals(userDao.findById(user.getId()).get().getPassword())) {
                userDao.update(user);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userDao.update(user);
            }

            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorsList));
        }
    }

    @DELETE
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@QueryParam("id") Long id) {
        UserEntity userToDelete = userDao.findById(id).orElse(null);
        ;

        if (userToDelete != null) {
            userDao.remove(userToDelete);

            return Response.ok(MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = new HashMap<>();

            errorList.put(CONST_ERROR, ERROR_USER_WITH_ID_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, CONST_ERRORS, errorList));
        }
    }
}
