package com.nix.zhylina.utils;

public class SqlQueriesConstants {
    public static final String SQL_FIND_BY_ID_ROLE = "from ROLEDAO role where role.id= :id";
    public static final String SQL_FIND_BY_NAME_ROLE = "from ROLEDAO role where role.name= :name";
    public static final String SQL_FIND_BY_LOGIN_USER = "from USERDAO user where user.login= :login";
    public static final String SQL_FIND_BY_EMAIL_USER = "from USERDAO user where user.email= :email";
    public static final String SQL_FIND_BY_ID_USER = "from USERDAO user where user.id= :id";
    public static final String SQL_FROM_USER = "from USERDAO";
    public static final String SQL_FROM_ROLE = "from ROLEDAO";

}
