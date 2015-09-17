package com.mmt.shubh.expensemanager.database.api.exceptions;

/**
 * Created by styagi on 5/31/2015.
 */
public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException() {
    }

    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentNotFoundException(Throwable cause) {
        super(cause);
    }

   /* public ContentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }*/
}
