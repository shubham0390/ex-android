package com.mmt.shubh.expensemanager.dagger.module.api;

import android.app.Application;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 10:50 PM
 * TODO:Add class comment.
 */
public interface IDataModule {
    SharedPreferences provideSharedPreferences(Application app);

    MemberDataAdapter provideMemberDataAdapter(Application application);
}
