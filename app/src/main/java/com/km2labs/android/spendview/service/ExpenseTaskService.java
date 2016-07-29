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
       /* DataAdapter<UserInfo> dataAdapter = new UserInfoSQLDataAdapter(getApplicationContext());
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(getApplicationContext());
        List<UserInfo> userInfos = dataAdapter.getAll();
        DeviceDetails details = new DeviceDetails(userInfos.get(0), deviceUuidFactory.getDeviceUuid().toString(), registrationToken);
*/
    }
}
