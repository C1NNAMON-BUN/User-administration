package com.nix.zhylina.rest;

import com.nix.zhylina.models.RoleEntity;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.exceptions.ApiError;
import com.nix.zhylina.exceptions.CustomErrorHandling;
import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.utils.AuthConstants;
import com.nix.zhylina.utils.Constants;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
public class RestService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithId(@QueryParam("id") Long id) {
        Optional<UserEntity> user = userService.findById(id);

        if (user.isPresent()) {
            return Response.ok(user.get(), MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = Map.of(Constants.CONST_ERROR, AuthConstants.ERROR_USER_WITH_ID_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERRORS, errorList));
        }

    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserEntity> users = userService.findAll();

        if (!users.isEmpty()) {
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = Map.of(Constants.CONST_ERROR, AuthConstants.ERROR_USERS_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERRORS, errorList));
        }
    }

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        List<RoleEntity> roles = roleService.findAll();

        if (!roles.isEmpty()) {
            return Response.ok(roles, MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = Map.of(Constants.CONST_ERROR, AuthConstants.ERROR_ROLES_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERRORS, errorList));
        }
    }

    @GET
    @Path("/role")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoleWithName(@QueryParam("name") String name) {
        Optional<RoleEntity> roleEntity = roleService.findByName(name);

        if (roleEntity.isPresent()) {
            return Response.ok(roleEntity.get(), MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = Map.of(Constants.CONST_ERROR, AuthConstants.ERROR_ROLE_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERRORS, errorList));
        }
    }

    @POST
    @Path("/registration")
    @Validated
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrationUser(@RequestBody UserEntity user) {
        Map<String, String> errorsList = validator.validateAddNewUser(user);

        if (errorsList.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.create(user);

            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERROR, errorsList));
        }
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@RequestBody UserEntity user) {
        Map<String, String> errorsList = validator.validateUpdateUser(user);

        if (errorsList.isEmpty() && userService.findById(user.getId()).isPresent()) {
            if (user.getPassword().equals(userService.findById(user.getId()).get().getPassword())) {
                userService.update(user);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.update(user);
            }

            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERROR, errorsList));
        }
    }

    @DELETE
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@QueryParam("id") Long id) {
        Optional<UserEntity> userToDelete = userService.findById(id);

        if (userToDelete.isPresent()) {
            userService.remove(userToDelete.get());

            return Response.ok(MediaType.APPLICATION_JSON).build();
        } else {
            Map<String, String> errorList = Map.of(Constants.CONST_ERROR, AuthConstants.ERROR_USER_WITH_ID_NOT_FOUND);

            return CustomErrorHandling.toResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.CONST_ERRORS, errorList));
        }
    }
}
