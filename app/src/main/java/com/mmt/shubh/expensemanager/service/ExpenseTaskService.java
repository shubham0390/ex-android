package com.mmt.shubh.expensemanager.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.mmt.shubh.expensemanager.DeviceUuidFactory;
import com.mmt.shubh.expensemanager.database.content.DeviceDetails;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.mmt.shubh.expensemanager.gsm.QuickstartPreferences;

import java.util.List;

import com.mmt.shubh.expensemanager.database.api.DataAdapter;

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
