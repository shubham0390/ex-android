package com.km2labs.android.spendview.home;

import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.expense.ExpenseFilter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SummaryActivityPresenter extends BasePresenter<ISummaryActivityView>
        implements MVPPresenter<ISummaryActivityView> {

    ExpenseDataAdapter expenseDataAdapter;

    @Inject
    public SummaryActivityPresenter(ExpenseDataAdapter expenseDataAdapter) {
        this.expenseDataAdapter = expenseDataAdapter;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseWithFilters(ExpenseFilter expenseFilter) {
        expenseDataAdapter.getExpensesWithFilters(expenseFilter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(expenses -> getView().showExpenseList(expenses), this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }
}
