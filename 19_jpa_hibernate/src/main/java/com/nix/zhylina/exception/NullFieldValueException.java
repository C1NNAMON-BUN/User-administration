package com.nix.zhylina.exception;

/**
 * <p><h2>NullFieldValueException</h2>
 * Exception if record is empty
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 11.03.2022 </p>
 */
public class NullFieldValueException extends RuntimeException {
    public NullFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
