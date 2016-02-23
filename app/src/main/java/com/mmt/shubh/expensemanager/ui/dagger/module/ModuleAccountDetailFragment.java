package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;
import com.mmt.shubh.expensemanager.ui.presenters.AccountDetailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 04/Nov/2015,
 * 5:11 PM
 * TODO:Add class comment.
 */
@Module
public class ModuleAccountDetailFragment {

    @Provides
    @ActivityScope
    public AccountDetailPresenter provideAccountListPresenter(AccountDataAdapter accountDataAdapter, ExpenseDataAdapter dataAdapter) {
        return new AccountDetailPresenter(accountDataAdapter,dataAdapter);
    }

    @ActivityScope
    @Provides
    public ExpenseDataAdapter provideExpenseDataAdapter(Context context) {
        return new ExpenseSqlDataAdapter(context);
    }
}
