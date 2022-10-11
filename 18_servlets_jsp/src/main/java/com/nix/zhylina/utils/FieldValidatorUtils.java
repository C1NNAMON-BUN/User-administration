package com.nix.zhylina.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains methods for validating password, mail, name and date
 * of birth. These methods are used in classes
 * {@link com.nix.zhylina.controllers.admin.AddUsersController} and
 * {@link com.nix.zhylina.controllers.admin.EditUsersController }
 *
 * @author Zhilina Svetlana
 * @version 2.0
 * @since 09.03.2022
 */
public class FieldValidatorUtils {
    public static boolean isLoginValid(String login) {
        String regex = "^[a-zA-Z0-9._-]{4,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean isNameValid(String login) {
        String regex = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){2,24}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        String regex = "(?=\\S+$).{4,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isDateValid(String date) {
        if (!date.isEmpty()) {
            LocalDate actualDate = Date.valueOf(date).toLocalDate();
            return actualDate.isAfter(LocalDate.now().minus(Period.ofYears(110))) &&
                    actualDate.isBefore(LocalDate.now());
        } else {
            return false;
        }
    }
}
