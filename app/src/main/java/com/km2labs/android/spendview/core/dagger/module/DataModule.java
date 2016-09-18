/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.core.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.AccountSQLDataAdapter;
import com.km2labs.android.spendview.App;
import com.km2labs.android.spendview.core.dagger.module.api.IDataModule;
import com.km2labs.android.spendview.database.DatabaseHelper;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.UserDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.CategorySQLDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.ExpenseSqlDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.MemberExpenseSQLDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.MemberSQLDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.TransactionSQLDataAdapter;
import com.km2labs.android.spendview.database.dataadapters.UserSQLDataAdapter;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class DataModule implements IDataModule {

    private static final String PREFERENCE_NAME = "expense_manager";

    private Context mContext;

    private BriteDatabase mBriteDatabase;

    public DataModule(App app) {
        mContext = app.getApplicationContext();
    }

    @Override
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public AccountDataAdapter provideAccountDataAdapter() {
        return new AccountSQLDataAdapter(getBriteDatabase());
    }

    @Singleton
    @Provides
    public ExpenseDataAdapter provideExpenseDataAdapter() {
        return new ExpenseSqlDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public TransactionDataAdapter provideTransactionDataAdapter() {
        return new TransactionSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public MemberExpenseDataAdapter provideMemberExpenseDataAdapter() {
        return new MemberExpenseSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public MemberDataAdapter provideMemberDataAdapter() {
        return new MemberSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    ExpenseBookDataAdapter provideExpenseBookDataAdapter() {
        return new ExpenseBookSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public UserDataAdapter provideUserDataAdapter() {
        return new UserSQLDataAdapter(getBriteDatabase());
    }

    @Provides
    @Singleton
    public CategoryDataAdapter provideCategoryDataAdapter() {
        return new CategorySQLDataAdapter(mBriteDatabase);
    }

    @Override
    @Provides
    @Singleton
    public BriteDatabase provideBriteDatabase() {
        return getBriteDatabase();
    }

    private BriteDatabase getBriteDatabase() {
        if (mBriteDatabase == null) {
            SqlBrite sqlBrite = SqlBrite.create(message -> {
                Timber.tag("SQLBrite");
                Timber.d(message);
            });

            mBriteDatabase = sqlBrite.wrapDatabaseHelper(new DatabaseHelper(mContext), Schedulers.io());
        }
        return mBriteDatabase;
    }
}
