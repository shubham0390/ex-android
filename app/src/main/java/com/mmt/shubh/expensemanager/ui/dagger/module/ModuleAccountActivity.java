package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.ui.presenters.AccountActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 2/20/16.
 */
@Module
public class ModuleAccountActivity {

    @Provides
    @ActivityScope
    public AccountActivityPresenter provideAccountListPresenter(AccountSQLDataAdapter adapter) {
        return new AccountActivityPresenter(adapter);
    }

    @Provides
    @ActivityScope
    public AccountDataAdapter provideAccountDataAdapter(Context context) {
        return new AccountSQLDataAdapter(context);
    }
}
