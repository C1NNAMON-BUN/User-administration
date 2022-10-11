package com.nix.zhylina.exception;

/**
 * The class that is called when the servlet is executed
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 22.02.2022 </p>
 */
public class ServletProcessingException extends RuntimeException {
    public ServletProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}