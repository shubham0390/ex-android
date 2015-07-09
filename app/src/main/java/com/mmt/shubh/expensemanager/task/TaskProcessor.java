/*******************************************************************************
 * Copyright (c) 2015 AirWatch, LLC. All rights reserved.
 * This product is protected by copyright and intellectual property laws in
 * the United States and other countries as well as by international treaties.
 * AirWatch products may be covered by one or more patents queueed at
 * http://www.vmware.com/go/patents.
 ******************************************************************************/

package com.mmt.shubh.expensemanager.task;

import android.text.TextUtils;


import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by STyagi on 6/10/2015.
 * <p/>
 * Execute series of task in parallel or serial. Task should be subclass of {@link AbstractTask}
 */
public class TaskProcessor {

    private BlockingDeque<ITask> mTaskQueue;

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    private WeakReference<OnTaskCompleteListener> mTaskCompleteListeners;


    private ITask mActive;

    public TaskProcessor() {
        mTaskQueue = new LinkedBlockingDeque<>(16);
    }

    /**
     * Add task at first position of task queue.
     *
     * @param task
     */
    public void addAtFrontOfQueue(ITask task) {
        if (task != null)
            mTaskQueue.addFirst(task);
    }

    /**
     * Add task to end of the queue
     *
     * @param task should be a subclass of {@link ITask}
     */
    public void addTask(ITask task) {
        if (task != null)
            mTaskQueue.add(task);
    }

    /**
     * Move any existing task to first position in the queue.
     *
     * @param task - {@link ITask}
     */
    public void moveToFirst(ITask task) {
        if (task != null) {
            mTaskQueue.remove(task);
            mTaskQueue.addFirst(task);
        }
    }

    /**
     * Move any existing task to end position in the queue.
     *
     * @param task - {@link ITask}
     */
    public void moveToEnd(ITask task) {
        if (task != null) {
            mTaskQueue.remove(task);
            mTaskQueue.add(task);
        }
    }

    /**
     * Start the execution of tasks
     */
    public void startExecution() {
        for (final ITask task : mTaskQueue) {
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    executeTask(task);
                }
            });
        }
    }

    public void execute(ITask task) {
        mTaskQueue.offer(task);
        if (mActive == null) {
            scheduleNext();
        }
    }

    private void scheduleNext() {
        if ((mActive = mTaskQueue.poll()) != null) {
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    executeTask(mActive);
                }
            });
        }
    }

    /*
     * Execute single task
     */
    private void executeTask(ITask task) {
        TaskResult result = task.execute();
        String action = task.getTaskAction();
        if (!TextUtils.isEmpty(action))
            notifyListener(action, result);

        task.onPostExecute(result);
    }

    public void removeOnTaskCompleteListener(OnTaskCompleteListener listener) {
        mTaskCompleteListeners.clear();
    }

    public ITask getFirstTask() {
        return mTaskQueue.peekFirst();
    }

    public ITask getLastTask() {
        return mTaskQueue.peekLast();
    }

    public void setOnTaskCompleteListener(OnTaskCompleteListener listener) {
        mTaskCompleteListeners = new WeakReference<>(listener);
    }

    public void notifyListener(String action, TaskResult result) {
        if (mTaskCompleteListeners != null && mTaskCompleteListeners.get() != null) {
            mTaskCompleteListeners.get().onTaskComplete(action, result);
        }
    }
}
