/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.service;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;

/**
 * Created by Subham Tyagi,
 * on 14/Oct/2015,
 * 10:13 AM
 * TODO:Add class comment.
 */
public final class ExpenseSyncManager {

    private static ExpenseSyncManager sExpenseSyncManager;

    private GcmNetworkManager mScheduler;

    public static ExpenseSyncManager getExpenseServiceManager(Context context) {

        if (sExpenseSyncManager == null) {
            sExpenseSyncManager = new ExpenseSyncManager(context);

        }
        return sExpenseSyncManager;
    }

    private ExpenseSyncManager(Context context) {
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
