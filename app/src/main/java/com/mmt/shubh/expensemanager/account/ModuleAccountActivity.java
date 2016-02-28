package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 2/20/16.
 */
@Module
public class ModuleAccountActivity {

    @Provides
    @ActivityScope
    public AccountActivityPresenter provideAccountListPresenter(AccountDataAdapter accountDataAdapter) {
        return new AccountActivityPresenter(accountDataAdapter);
    }


}
