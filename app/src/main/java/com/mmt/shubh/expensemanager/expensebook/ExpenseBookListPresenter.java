package com.mmt.shubh.expensemanager.expensebook;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;

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
public class ExpenseBookListPresenter extends MVPAbstractPresenter<MVPLCEView<List<ExpenseBook>>>
        implements MVPPresenter<MVPLCEView<List<ExpenseBook>>> {

    ExpenseBookDataAdapter mExpenseBookDataAdapter;

    @Inject
    public ExpenseBookListPresenter(ExpenseBookDataAdapter adapter) {
        mExpenseBookDataAdapter = adapter;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }


    public void deleteExpenseBook(long id) {
        mExpenseBookDataAdapter.delete(id);
    }

    public void loadExpenseBookList() {
        mExpenseBookDataAdapter.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().setData(d), e -> getView().showError(e, false));

    }

}

