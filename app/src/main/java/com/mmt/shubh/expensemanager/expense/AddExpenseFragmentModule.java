package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 3/12/16.
 */
@Module
public class AddExpenseFragmentModule {

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
    public AddExpensePresenter provideAddExpensePresenter(ExpenseModel expenseModel) {
        return new AddExpensePresenter(expenseModel);
    }
}
