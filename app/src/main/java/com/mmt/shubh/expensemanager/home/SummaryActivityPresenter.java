package com.mmt.shubh.expensemanager.home;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseFilter;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SummaryActivityPresenter extends MVPAbstractPresenter<ISummaryActivityView>
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
