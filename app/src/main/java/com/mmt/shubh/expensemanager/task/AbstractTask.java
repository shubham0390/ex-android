/*******************************************************************************
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 * This product is protected by copyright and intellectual property laws in
 * the United States and other countries as well as by international treaties.
 * AirWatch products may be covered by one or more patents listed at
 * http://www.vmware.com/go/patents.
 ******************************************************************************/

package com.mmt.shubh.expensemanager.task;

import android.content.Context;

/**
 * Created by Subham Tyagi,
 * on 11/Jun/2015,
 * 11:53 AM
 * <p/>
 * Base class for SDK defined tasks which will execute by {@link TaskProcessor}.
 */
public abstract class AbstractTask<T> implements ITask<T> {

    protected TaskResult<T> mTaskResult;

    protected Context mContext;

    public TaskResult getTaskResult() {
        return mTaskResult;
    }

    public AbstractTask(Context context) {
        mContext = context;
        mTaskResult = new TaskResult<>();
    }

    @Override
    public void onPostExecute(TaskResult<T> result) {
    }

    protected void createTaskResult(boolean success, int statusCode, T result) {
        mTaskResult.setIsSuccess(success);
        mTaskResult.setResult(result);
        mTaskResult.setStatusCode(statusCode);
    }

    protected void createTaskResult(boolean success, int statusCode, Exception error) {
        mTaskResult.setIsSuccess(success);
        mTaskResult.setStatusCode(statusCode);
        mTaskResult.setException(error);
    }

}

