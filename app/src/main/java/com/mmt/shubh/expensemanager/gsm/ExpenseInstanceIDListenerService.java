package com.mmt.shubh.expensemanager.gsm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by styagi on 6/3/2015.
 */
public class ExpenseInstanceIDListenerService extends InstanceIDListenerService {

    public ExpenseInstanceIDListenerService() {
        super();
    }
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
    // [END refresh_token]
}
