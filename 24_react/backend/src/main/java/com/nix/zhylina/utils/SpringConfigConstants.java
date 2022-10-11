package com.nix.zhylina.utils;

public class SpringConfigConstants {
    public static final String PROPERTY_SPRING_VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    public static final String PROPERTY_SPRING_VIEW_RESOLVER_SUFFIX = ".jsp";
    public static final String PROPERTY_SPRING_ADD_RESOURCE_HANDLER = "/WEB-INF/**";
    public static final String PROPERTY_SPRING_ADD_RESOURCE_LOCATIONS = "classpath:/WEB-INF/";

    public static final String PROPERTY_SPRING_SET_DATE_FORMAT = "dd.MM.yyyy";
    public static final String PROPERTY_SPRING_SET_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String PROPERTY_SPRING_REGISTER_DATE_FORMAT = "yyyy.MM.dd";

    public static final String ANNOTATION_SPRING_PROPERTY_SOURCE = "application.properties";
    public static final String ANNOTATION_SPRING_COMPONENT_SCAN = "com.nix.zhylina";

    public static final String ANT_MATCHER_USER_PAGE = "/user";
    public static final String LOGIN_PAGE = "/login";
    public static final String REGISTRATION_PAGE = "/registration";
    public static final String ROLES_REST = "/roles";
    public static final String ROLE_REST = "/role";
    public static final String ANT_MATCHER_ROLE_ADMIN = "ADMIN";

    public static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    public static final String CAPTCHA_SECRET_BACKEND = "captcha.secret.backend";
}
