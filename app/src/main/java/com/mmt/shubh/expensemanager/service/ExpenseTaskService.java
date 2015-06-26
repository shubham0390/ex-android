package com.mmt.shubh.expensemanager.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.mmt.shubh.expensemanager.DeviceUuidFactory;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.DeviceDetails;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.gsm.QuickstartPreferences;

import java.util.List;

import api.DataAdapter;

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
            case GCMTaskConstant.TAG_UPLOAD_EXPENSE:
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
        DataAdapter<Account> dataAdapter = new AccountSQLDataAdapter(getApplicationContext());
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(getApplicationContext());
        List<Account> accounts = dataAdapter.getAll();
        DeviceDetails details = new DeviceDetails(accounts.get(0), deviceUuidFactory.getDeviceUuid().toString(), registrationToken);
    }
}
