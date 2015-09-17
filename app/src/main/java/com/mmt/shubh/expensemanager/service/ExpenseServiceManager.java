package com.mmt.shubh.expensemanager.service;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.mmt.shubh.expensemanager.database.content.DeviceDetails;

/**
 * Created by styagi on 6/3/2015.
 */
public final class ExpenseServiceManager {

    private static ExpenseServiceManager sExpenseServiceManager;

    private GcmNetworkManager mScheduler;

    public static ExpenseServiceManager getExpenseServiceManager(Context context) {

        if (sExpenseServiceManager == null) {
            sExpenseServiceManager = new ExpenseServiceManager(context);

        }
        return sExpenseServiceManager;
    }

    private ExpenseServiceManager(Context context) {
        mScheduler = GcmNetworkManager.getInstance(context);
    }

    public void registerDeviceWithServer() {

        long startSecs = 0L; // allow for execution of the task as soon as possible (0s from now)

        long endSecs = startSecs + 3600L; // task will be run within 1 min of start time

        OneoffTask oneoff = new OneoffTask.Builder()
                .setService(ExpenseTaskService.class)
                .setTag(GCMTaskConstant.TAG_REGISTRATION)
                .setExecutionWindow(startSecs, endSecs)
                .setRequiredNetwork(com.google.android.gms.gcm.Task.NETWORK_STATE_CONNECTED)
                .build();
        mScheduler.schedule(oneoff);
    }

    public void syncExpense() {

    }
}
