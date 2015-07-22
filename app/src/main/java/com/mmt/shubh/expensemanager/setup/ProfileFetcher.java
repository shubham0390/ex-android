package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

/**
 * Created by styagi on 6/4/2015.
 */
public abstract class ProfileFetcher {

    public abstract UserInfo fetchUserAccountDetails(Context context);

    public void saveUser(Context context, UserInfo userInfo) {
        UserInfoSQLDataAdapter sqlDataAdapter =  new UserInfoSQLDataAdapter(context);
        sqlDataAdapter.create(userInfo);
    }
    public void update(Context context, UserInfo userInfo){
        UserInfoSQLDataAdapter sqlDataAdapter =  new UserInfoSQLDataAdapter(context);
        sqlDataAdapter.update(userInfo);
    }
}
