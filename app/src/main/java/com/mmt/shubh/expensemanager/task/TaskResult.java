/*******************************************************************************
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 * This product is protected by copyright and intellectual property laws in
 * the United States and other countries as well as by international treaties.
 * AirWatch products may be covered by one or more patents listed at
 * http://www.vmware.com/go/patents.
 ******************************************************************************/

package com.mmt.shubh.expensemanager.task;

import android.os.Parcel;

/**
 * Execution result of {@link ITask}
 *
 * Created by Subham Tyagi,
 * on 11/Jun/2015,
 * 12:48 PM
 */
public final class TaskResult<T>  {
    /**
     * Task is success of failed
     */
    private boolean mIsSuccess;

    /**
     * Result status code.
     * See{@link TaskResultStatus}
     */
    private int mStatusCode;

    /**
     * Payload of task
     */
    private T mResult;

    private Exception mException;

    public TaskResult() {
    }

    public TaskResult(boolean isSuccess, T result, int statusCode) {
        mIsSuccess = isSuccess;
        mResult = result;
        mStatusCode = statusCode;
    }

    protected TaskResult(Parcel in) {
        mStatusCode = in.readInt();
    }


    public TaskResult setResult(T result) {
        mResult = result;
        return this;
    }

    public T getResult() {
        return mResult;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public TaskResult setStatusCode(int statusCode) {
        mStatusCode = statusCode;
        return this;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        mIsSuccess = isSuccess;
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }
}
