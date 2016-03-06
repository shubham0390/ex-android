package com.mmt.shubh.expensemanager.expensebook;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;

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
    ExpenseBookModel provideExpenseBookModel(Context context, ExpenseBookDataAdapter expenseBookDataAdapter,
                                             MemberDataAdapter memberDataAdapter) {
        return new ExpenseBookModel(context, expenseBookDataAdapter, memberDataAdapter);
    }

    @Provides
    @ActivityScope
    ExpenseBookListPresenter provideExpenseBookListPresenter(ExpenseBookModel expenseBookModel) {
        return new ExpenseBookListPresenter(expenseBookModel);
    }

}
