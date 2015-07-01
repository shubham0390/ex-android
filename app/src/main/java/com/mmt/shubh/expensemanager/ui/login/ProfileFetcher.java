package com.mmt.shubh.expensemanager.ui.login;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.UserInfo;

/**
 * Created by styagi on 6/4/2015.
 */
public interface ProfileFetcher {

    UserInfo fetchUserAccountDetails(Context context);
}
