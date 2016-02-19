package com.mmt.shubh.expensemanager.ui.presenters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.exception.EmptyDataException;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseFilter;
import com.mmt.shubh.expensemanager.ui.models.ExpenseModel;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 09/Nov/2015,
 * 10:47 PM
 * TODO:Add class comment.
 */
public class ExpenseListPresenter extends MVPAbstractPresenter<MVPLCEView<List<ExpenseListViewModel>>>
        implements MVPPresenter<MVPLCEView<List<ExpenseListViewModel>>> {

    private String  LOG_TAG = getClass().getName();

    protected Context mContext;

    @Inject
    ExpenseModel mExpenseModel;

    @Inject
    public ExpenseListPresenter(Context context) {
        mContext = context;
        mExpenseModel = new ExpenseModel(mContext);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseForMember(long memberId) {
        mExpenseModel.loadExpenseWithFilter(memberId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mExpenseObserver);
    }

    private Observer<List<ExpenseListViewModel>> mExpenseObserver = new Observer<List<ExpenseListViewModel>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.alwaysLog(LOG_TAG,e.getMessage());
            getView().showError(e, false);
        }

        @Override
        public void onNext(List<ExpenseListViewModel> expenses) {
            if (expenses == null || expenses.isEmpty()) {
                getView().showError(new EmptyDataException("No Expense Present"), false);
            } else
                getView().setData(expenses);
        }
    };


    public void loadExpenseWithFilter(ExpenseFilter filter) {
        // TODO: 11/12/2015 remove this after testing
        filter.setAccountId(1);
        mExpenseModel.loadExpenseWithFilter(filter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mExpenseObserver);
    }
}