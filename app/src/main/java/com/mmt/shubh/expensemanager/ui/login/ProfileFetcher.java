package com.mmt.shubh.expensemanager.ui.login;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.Account;

/**
 * Created by styagi on 6/4/2015.
 */
public interface ProfileFetcher {

    Account fetchUserAccountDetails(Context context);
}
