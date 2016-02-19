package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 04/Nov/2015,
 * 5:11 PM
 * TODO:Add class comment.
 */
@Module
public class ModuleAccountListFragment {

   /* @Provides
    @ActivityScope
    public AccountListPresenter provideAccountListPresenter() {
        return new AccountListPresenter();
    }*/
    @Provides
    @ActivityScope
    public AccountDataAdapter provideAccountDataAdapter(Context context) {
        return new AccountSQLDataAdapter(context);
    }
}
