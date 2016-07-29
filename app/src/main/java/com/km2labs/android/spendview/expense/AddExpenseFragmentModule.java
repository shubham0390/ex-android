package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;

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
