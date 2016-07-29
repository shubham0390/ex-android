package com.km2labs.android.spendview.expensebook.detail;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.expensebook.ExpenseBookModel;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

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
@ConfigPersistent
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

