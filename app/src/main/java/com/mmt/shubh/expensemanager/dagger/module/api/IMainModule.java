package com.mmt.shubh.expensemanager.dagger.module.api;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 10:48 PM
 * TODO:Add class comment.
 */
public interface IMainModule {

    Application provideExpenseApplication();

    Resources provideResources();

    Context provideApplicationContext();

    ContentResolver provideContentResolver();

}
