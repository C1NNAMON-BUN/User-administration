package com.nix.zhylina.validators;

import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.models.UserEntity;

import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERRORS_ISNT_EXIST;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_2_PASSWORDS_ARENT_EQUAL;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_2_PASSWORDS_MUST_BE_FILLED;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_EMAIL_IS_EXIST;
import static com.nix.zhylina.utils.ValidationMessageConstants.VALIDATOR_ERROR_LOGIN_IS_EXIST;

public class Validator {
    private Validator() {
    }

    public static boolean isValidateUserToUpdate(IUserService hibernateUserDao, UserEntity userToUpdate) {
        UserEntity currentUser = hibernateUserDao.findByEmail(userToUpdate.getEmail()).orElse(null);
        return isValidateEmailForUpdate(currentUser, userToUpdate);
    }

    private static boolean isValidateEmailForUpdate(UserEntity currentUser, UserEntity userToUpdate) {
        if (currentUser == null) {
            return false;
        } else {
            return currentUser.getEmail().equals(userToUpdate.getEmail());
        }
    }

    public static boolean isValidateUpdatePassword(String firstPassword, String secondPassword) {
        return !firstPassword.equals(secondPassword) && !(firstPassword).isEmpty();
    }

    public static String validateAddPassword(String firstPassword, String secondPassword) {
        if (!firstPassword.equals(secondPassword)) {
            return VALIDATOR_ERROR_2_PASSWORDS_ARENT_EQUAL;
        } else if (firstPassword.isEmpty()) {
            return VALIDATOR_ERROR_2_PASSWORDS_MUST_BE_FILLED;
        } else {
            return VALIDATOR_ERRORS_ISNT_EXIST;
        }
    }

    public static String validateAddNewUser(IUserService hibernateUserDao, UserEntity userToAdd) {
        if (!isValidateAddNewUserEmail(hibernateUserDao, userToAdd)) {
            return VALIDATOR_ERROR_EMAIL_IS_EXIST;
        } else if (!isValidateAddNewUserLogin(hibernateUserDao, userToAdd)) {
            return VALIDATOR_ERROR_LOGIN_IS_EXIST;
        } else {
            return VALIDATOR_ERRORS_ISNT_EXIST;
        }
    }

    public static boolean isValidateAddNewUserLogin(IUserService hibernateUserDao, UserEntity userToAdd) {
        return !hibernateUserDao.findByLogin(userToAdd.getLogin()).isPresent();
    }

    public static boolean isValidateAddNewUserEmail(IUserService hibernateUserDao, UserEntity userToAdd) {
        return !hibernateUserDao.findByEmail(userToAdd.getEmail()).isPresent();
    }
}
