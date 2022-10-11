package com.nix.zhylina.validators;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.Constants.ERROR_USER_WITH_ID_NOT_FOUND;
import static com.nix.zhylina.utils.ValidationMessageConstants.CONST_VALUE_EMAIL;
import static com.nix.zhylina.utils.ValidationMessageConstants.CONST_VALUE_LOGIN;
import static com.nix.zhylina.utils.ValidationMessageConstants.ERROR_INCORRECT_EMAIL;
import static com.nix.zhylina.utils.ValidationMessageConstants.ERROR_INCORRECT_LOGIN;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_EMAIL_IS_EXIST;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_LOGIN_IS_EXIST;

@Component
public class Validator {
   private final IUserService userDao;

    @Autowired
    public Validator(IUserService userDao) {
        this.userDao = userDao;
    }

    public Map<String, String> validateAddNewUser(UserEntity user) {
        Map<String, String> errorsList = new HashMap<>();

        if (userDao.findByLogin(user.getLogin()).isPresent()) {
            errorsList.put(CONST_VALUE_LOGIN, VALIDATOR_ERROR_LOGIN_IS_EXIST);
        }
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            errorsList.put(CONST_VALUE_EMAIL, VALIDATOR_ERROR_EMAIL_IS_EXIST);
        }
        return errorsList;
    }

    public Map<String, String> validateUpdateUser(UserEntity user) {
        Map<String, String> errorsList = new HashMap<>();
        UserEntity userToUpdate = userDao.findById(user.getId()).orElse(null);;

        if (userToUpdate != null) {
            if (!userToUpdate.getLogin().equals(user.getLogin())) {
                errorsList.put(CONST_VALUE_LOGIN, ERROR_INCORRECT_LOGIN);
            }
            if (!userToUpdate.getEmail().equals(user.getEmail())) {
                errorsList.put(CONST_VALUE_EMAIL, ERROR_INCORRECT_EMAIL);
            }
        } else {
            errorsList.put(CONST_ERROR, ERROR_USER_WITH_ID_NOT_FOUND);
        }

        return errorsList;
    }
}
