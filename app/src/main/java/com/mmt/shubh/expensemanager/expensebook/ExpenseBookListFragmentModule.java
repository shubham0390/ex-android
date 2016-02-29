package com.mmt.shubh.expensemanager.expensebook;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 10:50 PM
 * TODO:Add class comment.
 */

@Module
public class ExpenseBookListFragmentModule {

    @Provides
    @ActivityScope
    ExpenseBookListPresenter provideExpenseBookListPresenter(ExpenseBookDataAdapter adapter) {
        return new ExpenseBookListPresenter(adapter);
    }

}
