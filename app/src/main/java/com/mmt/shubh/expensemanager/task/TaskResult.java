/*******************************************************************************
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 * This product is protected by copyright and intellectual property laws in
 * the United States and other countries as well as by international treaties.
 * AirWatch products may be covered by one or more patents listed at
 * http://www.vmware.com/go/patents.
 ******************************************************************************/

package com.mmt.shubh.expensemanager.task;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Execution result of {@link ITask}
 *
 * Created by Subham Tyagi,
 * on 11/Jun/2015,
 * 12:48 PM
 */
public final class TaskResult implements Parcelable {
    /**
     * Task is success of failed
     */
    private boolean mIsSuccess;

    /**
     * Result status code.
     * See{@link TaskResultStatus}
     */
    private int mStatus;

    /**
     * Payload of task
     */
    private String mPayload;

    public TaskResult() {
    }

    public TaskResult(boolean isSuccess, String payload, int status) {
        mIsSuccess = isSuccess;
        mPayload = payload;
        mStatus = status;
    }

    protected TaskResult(Parcel in) {
        mStatus = in.readInt();
        mPayload = in.readString();
    }

    public static final Creator<TaskResult> CREATOR = new Creator<TaskResult>() {
        @Override
        public TaskResult createFromParcel(Parcel in) {
            return new TaskResult(in);
        }

        @Override
        public TaskResult[] newArray(int size) {
            return new TaskResult[size];
        }
    };

    public Object getPayload() {
        return mPayload;
    }

    public TaskResult setPayload(String payload) {
        mPayload = payload;
        return this;
    }

    public int getStatus() {
        return mStatus;
    }

    public TaskResult setStatus(int status) {
        mStatus = status;
        return this;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        mIsSuccess = isSuccess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStatus);
        dest.writeString(mPayload);
    }
}
