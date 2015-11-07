package com.mmt.shubh.expensemanager.database.exception;

/**
 * Created by Subham Tyagi,
 * on 06/Nov/2015,
 * 6:31 PM
 * TODO:Add class comment.
 */
public class EmptyDataException extends RuntimeException {

    public EmptyDataException() {
    }

    public EmptyDataException(String detailMessage) {
        super(detailMessage);
    }

    public EmptyDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public EmptyDataException(Throwable throwable) {
        super(throwable);
    }
}
