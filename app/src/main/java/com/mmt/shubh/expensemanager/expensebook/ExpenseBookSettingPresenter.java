package com.mmt.shubh.expensemanager.expensebook;

import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:58 PM
 * TODO:Add class comment.
 */
public class ExpenseBookSettingPresenter extends MVPAbstractPresenter<IExpenseBookSettingView> implements MVPPresenter<IExpenseBookSettingView> {

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
