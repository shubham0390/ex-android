package com.mmt.shubh.expensemanager.task;

/**
 * Interface for notifying on SDK task complete.
 * <p/>
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 12:11 PM
 */
public interface OnTaskCompleteListener {
    /**
     * Method will we called when task has finished is it's execution
     *
     * @param action     - Task Action.
     * @param taskResult Result of task {@link TaskResult}.
     *                   See {@link TaskResultStatus}
     */
    void onTaskComplete(String action, TaskResult taskResult);
}
