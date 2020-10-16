package com.slobodianiuk.hotel.exceptions;

/**
 * @author Yaroslav Slobodianiuk
 */
public class DBException extends Exception{
    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
