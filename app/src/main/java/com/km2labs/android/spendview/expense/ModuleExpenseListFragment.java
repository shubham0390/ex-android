package com.km2labs.android.spendview.expense;

import android.content.Context;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewState;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewStateImpl;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 2/27/16.
 */
@Module
public class ModuleExpenseListFragment {

    @Provides
    @ActivityScope
    public ExpenseListPresenter provideExpenseListPresenter(Context context, ExpenseModel expenseModel) {
        return new ExpenseListPresenter(expenseModel, context);
    }

    @Provides
    @ActivityScope
    LCEViewState<List<ExpenseListViewModel>, MVPLCEView<List<ExpenseListViewModel>>> provideViewLCEViewState() {
        return new LCEViewStateImpl<>();
    }

    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                                            MemberExpenseDataAdapter memberDataAdapter,
                                            TransactionDataAdapter transactionDataAdapter,
                                            AccountDataAdapter accountDataAdapter,
                                            MemberDataAdapter memberData, ExpenseBookDataAdapter expenseBookDataAdapter) {
        return new ExpenseModel(expenseDataAdapter, memberDataAdapter, transactionDataAdapter,
                accountDataAdapter, memberData,expenseBookDataAdapter);
    }
}
