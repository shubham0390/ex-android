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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.km2labs.android.spendview.gsm.QuickstartPreferences;

public class ExpenseTaskService extends GcmTaskService {


    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        String task = taskParams.getTag();
        switch (task) {
            case GCMTaskConstant.TAG_REGISTRATION:
                doUserRegistration();
                break;
            case GCMTaskConstant.TAG_SYNC_EXPENSE:
                sendExpenseDetails();
                break;
        }
        return 0;
    }

    private void sendExpenseDetails() {

    }

    private void doUserRegistration() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String registrationToken = sharedPreferences.getString(QuickstartPreferences.REGISTRATION_TOKEN, "");
       /* DataAdapter<User> dataAdapter = new UserInfoSQLDataAdapter(getApplicationContext());
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(getApplicationContext());
        List<User> userInfos = dataAdapter.getAll();
        Device details = new Device(userInfos.get(0), deviceUuidFactory.getDeviceUuid().toString(), registrationToken);
*/
    }
}
