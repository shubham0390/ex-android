package com.km2labs.android.spendview.core.dagger.module.api;

import android.content.SharedPreferences;

import com.squareup.sqlbrite.BriteDatabase;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 10:50 PM
 * TODO:Add class comment.
 */
public interface IDataModule {
    SharedPreferences provideSharedPreferences();

    BriteDatabase provideBriteDatabase();
}
