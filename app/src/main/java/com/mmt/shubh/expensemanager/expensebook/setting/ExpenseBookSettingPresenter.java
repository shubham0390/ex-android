package com.mmt.shubh.expensemanager.expensebook.setting;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 16/Sep/2015,
 * 4:58 PM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class ExpenseBookSettingPresenter extends BasePresenter<IExpenseBookSettingView> implements MVPPresenter<IExpenseBookSettingView> {

    ExpenseBookModel mExpenseBookModel;

    @Inject
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
