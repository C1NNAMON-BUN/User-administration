package com.nix.zhylina.utils;

public class Constants {
    public static final String CONST_ERRORS = "errors";
    public static final int MAX_INPUT_SYMBOLS = 100;
    public static final int MIN_INPUT_SYMBOLS = 4;

    public static final String REGEX_FOR_ONLY_LETTER_AND_NUMBER = "^[a-zA-Z0-9_.-]*$";
    public static final String REGEX_FOR_EMAIL = "^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,3}$";

    public static final String  CONST_ERROR = "error";
    public static final String CONST_ID = "id";
    public static final String CONST_OBJECT_TO_EDIT = "objecttoedit";
    public static final String CONST_ALL_ROLES = "allRoles";
    public static final String CONST_USERSLIST = "userslist";
    public static final String CONST_ID_ROLE = "idRole";
    public static final String CONST_G_RECAPTCHA_RESPONSE = "g-recaptcha-response";
    public static final String CONST_PASSWORD = "password";
    public static final String CONST_PASSWORD_SECOND = "password2";
    public static final String CONST_CURRENT_USER = "currentUser";
    public static final String CONST_AUTHORITY_ROLE_PREFIX = "ROLE_";

    //CXF CONFIGURATION
    public static final String DEFAULT_ADDRESS_OF_LOCALHOST = "/";
    // RESPONSE CONST
    public static final String CONST_VALUE_STATUS_CODE = "StatusCode";
    // TOKEN CONST AND SETTINGS
    public static final String ERROR_TOKEN = "TokenError";
    public static final String ERROR_AUTH_CREDENTIALS_EXPIRED_OR_INVALID = "JWT token is expired or invalid";
    public static final String CONST_VALUE_TOKEN = "Token";
    public static final String CONST_VALUE_ROLES = "roles";
    public static final String CONST_PREFIX_BEARER = "Bearer_";
    public static final long MAX_VALIDITY_TIME = 5184000L;
    //AUTH
    public static final String CONST_PREFIX_USERNAME_NOT_FOUND = "User with username: ";
    public static final String CONST_ENDING_USERNAME_NOT_FOUND = " not found";
    public static final String CONST_VALUE_USERNAME = "username";
    public static final String CONST_VALUE_TOKEN_LOWERCASE = "token";
    public static final String ERROR_VALUE_INCORRECT_LOGIN_OR_PASSWORD = "Incorrect login or password";
    public static final String ERROR_USER_WITH_ID_NOT_FOUND = "User not found with this id.";
    public static final String ERROR_USERS_NOT_FOUND = "Users not found.";

}
