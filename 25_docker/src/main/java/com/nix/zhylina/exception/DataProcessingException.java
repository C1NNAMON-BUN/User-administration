package com.nix.zhylina.exception;

/**
 * <p><h2>DataProcessingException</h2>
 * <b>This exception will be thrown in case of errors
 * {@link java.sql.SQLException} and {@link java.io.IOException}</b><br >
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}