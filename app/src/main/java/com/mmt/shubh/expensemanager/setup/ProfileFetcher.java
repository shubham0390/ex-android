package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 25/Oct/2015,
 * 5:09 PM
 * TODO:Add class comment.
 */
public abstract class ProfileFetcher {

    public abstract Observable<UserInfo> fetchUserAccountDetails();

}
