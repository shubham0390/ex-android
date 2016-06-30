package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class SharedExpenseFragmentPresenter extends BasePresenter<SharedExpenseView>
        implements MVPPresenter<SharedExpenseView> {

    private ExpenseModel mExpenseModel;

    public SharedExpenseFragmentPresenter(ExpenseModel mExpenseModel) {
        this.mExpenseModel = mExpenseModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void getExpenseBookByMemberId(long memberId) {
        mExpenseModel.loadAllExpenseBookForMember(memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> getView().onExpenseBookListLoad(data), this::showError);
    }

    public void getExpenseBook(long expenseBookId) {
        mExpenseModel.getExpenseBook(expenseBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(expenseBook -> {
                    getView().onExpenseBookLoad(expenseBook);
                });
    }

    private void showError(Throwable throwable) {
        Timber.tag(throwable.getMessage());
    }
}
