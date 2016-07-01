package com.mmt.shubh.expensemanager.expensebook.detail;

import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 10:07 PM
 * TODO:Add class comment.
 */
public class ExpenseBookActivityPresenter extends BasePresenter<MVPLCEView<List<ExpenseBook>>>
        implements MVPPresenter<MVPLCEView<List<ExpenseBook>>> {

    ExpenseBookModel mExpenseBookModel;

    @Inject
    public ExpenseBookActivityPresenter(ExpenseBookModel adapter) {
        mExpenseBookModel = adapter;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }



    public void loadExpenseBookList() {
        mExpenseBookModel.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().setData(d), e -> getView().showError(e, false));

    }

}

