package com.nix.zhylina.validators;

import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.hibernateService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.nix.zhylina.utils.AuthConstants.ERROR_USER_WITH_ID_NOT_FOUND;
import static com.nix.zhylina.utils.Constants.CONST_ERROR;
import static com.nix.zhylina.utils.ValidationMessageConstants.CONST_VALUE_EMAIL;
import static com.nix.zhylina.utils.ValidationMessageConstants.CONST_VALUE_LOGIN;
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
        Optional<UserEntity> userToUpdate = userDao.findById(user.getId());

        if(userToUpdate.isPresent()) {
            if (!userToUpdate.get().getLogin().equals(user.getLogin())) {
                if (userDao.findByLogin(user.getLogin()).isPresent()) {
                    errorsList.put(CONST_VALUE_LOGIN, VALIDATOR_ERROR_LOGIN_IS_EXIST);
                }else {
                    errorsList.put(CONST_VALUE_LOGIN, ERROR_INCORRECT_LOGIN);
                }
            }
            if (!userToUpdate.get().getEmail().equals(user.getEmail())) {
                if (userDao.findByEmail(user.getEmail()).isPresent()) {
                    errorsList.put(CONST_VALUE_EMAIL, VALIDATOR_ERROR_EMAIL_IS_EXIST);
                }
            }

        } else {
            errorsList.put(CONST_ERROR, ERROR_USER_WITH_ID_NOT_FOUND);
        }

        return errorsList;
    }
}
