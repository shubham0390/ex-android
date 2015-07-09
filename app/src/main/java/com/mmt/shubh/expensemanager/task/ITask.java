/*******************************************************************************
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 * This product is protected by copyright and intellectual property laws in
 * the United States and other countries as well as by international treaties.
 * AirWatch products may be covered by one or more patents listed at
 * http://www.vmware.com/go/patents.
 ******************************************************************************/

package com.mmt.shubh.expensemanager.task;


/**
 * Created by Subham Tyagi,
 * on 11/Jun/2015,
 * 11:52 AM
 * android-agent
 */
public interface ITask {

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     *
     * @see #onPostExecute
     */
    TaskResult execute();

    /**
     * <p>Runs on the UI thread after {@link #execute()}. The
     * specified result is the value returned by {@link #execute()}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #execute()}.
     * @see #execute()
     */
    void onPostExecute(TaskResult result);

    String getTaskAction();

}
