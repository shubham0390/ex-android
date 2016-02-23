package com.mmt.shubh.network;

/**
 * Created by Subham Tyagi,
 * on 02/Nov/2015,
 * 5:16 PM
 * TODO:Add class comment.
 */
public class NoInternetConnectionException extends RuntimeException {

    public NoInternetConnectionException() {
    }

    public NoInternetConnectionException(String detailMessage) {
        super(detailMessage);
    }

    public NoInternetConnectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NoInternetConnectionException(Throwable throwable) {
        super(throwable);
    }
}
