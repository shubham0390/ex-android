package com.mmt.shubh.expensemanager.expensebook.detail;

import android.content.Context;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 10:54 PM
 * TODO:Add class comment.
 */
@Module
public class ExpenseBookActivityModule {

    @Provides
    @ActivityScope
    ExpenseBookModel provideExpenseBookModel(Context context, ExpenseBookDataAdapter expenseBookDataAdapter,
                                             MemberDataAdapter memberDataAdapter) {
        return new ExpenseBookModel(context, expenseBookDataAdapter, memberDataAdapter);
    }

    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(ExpenseDataAdapter expenseDataAdapter, MemberExpenseDataAdapter memberExpenseDataAdapter,
                                            TransactionDataAdapter transactionDataAdapter, AccountDataAdapter accountDataAdapter,
                                            ExpenseBookDataAdapter expenseBookDataAdapter
            , MemberDataAdapter memberDataAdapter) {
        return new ExpenseModel(expenseDataAdapter, memberExpenseDataAdapter,
                transactionDataAdapter, accountDataAdapter, memberDataAdapter, expenseBookDataAdapter);
    }

    @Provides
    @ActivityScope
    ExpenseBookActivityPresenter provideExpenseBookListPresenter(ExpenseBookModel expenseBookModel) {
        return new ExpenseBookActivityPresenter(expenseBookModel);
    }

    @Provides
    @ActivityScope
    ExpenseBookDetailPresenter provideExpenseBookDetailPresenter(ExpenseModel expenseBookModel) {
        return new ExpenseBookDetailPresenter(expenseBookModel);
    }
}
