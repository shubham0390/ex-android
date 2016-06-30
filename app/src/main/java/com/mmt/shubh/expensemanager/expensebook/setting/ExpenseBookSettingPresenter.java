package com.mmt.shubh.expensemanager.expensebook.setting;

import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:58 PM
 * TODO:Add class comment.
 */
public class ExpenseBookSettingPresenter extends BasePresenter<IExpenseBookSettingView> implements MVPPresenter<IExpenseBookSettingView> {

    ExpenseBookModel mExpenseBookModel;

    public ExpenseBookSettingPresenter(ExpenseBookModel expenseBookModel) {
        mExpenseBookModel = expenseBookModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadOwnerDetails(long ownerId) {

        mExpenseBookModel.loadExpenseBookOwner(ownerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().onOwnerLoaded(d));
    }
}
