package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.ui.presenters.ExpenseBookListPresenter;

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
    ExpenseBookDataAdapter provideExpenseBookDataAdapter(Context context) {
        return new ExpenseBookSQLDataAdapter(context);
    }
    @Provides
    @ActivityScope
    ExpenseBookListPresenter provideExpenseBookListPresenter(Context context,ExpenseBookDataAdapter adapter) {
        return new ExpenseBookListPresenter(context,adapter);
    }

}
