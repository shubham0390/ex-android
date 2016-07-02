package com.mmt.shubh.expensemanager.expensebook.add;

import android.os.Bundle;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.utils.Constants;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import org.parceler.Parcels;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 11/Sep/2015,
 * 5:14 PM
 */
@ConfigPersistent
public class ExpenseBookFragmentPresenter extends BasePresenter<IExpenseBookFragmentView>
        implements MVPPresenter<IExpenseBookFragmentView> {

    private ExpenseBookModel mExpenseBookModel;

    @Inject
    public ExpenseBookFragmentPresenter(ExpenseBookModel expenseBookModel) {
        mExpenseBookModel = expenseBookModel;
    }

    public void validateExpenseNameAndProceed(String expenseName, String mOutputFileUri,
                                              String expenseDescription, boolean isUpdate) {
        if (TextUtils.isEmpty(expenseName)) {
            getView().showEmptyError();
            return;
        }

        ExpenseBook expenseBook = new ExpenseBook();

        expenseBook.setName(expenseName);
        expenseBook.setProfileImagePath(mOutputFileUri);
        expenseBook.setDescription(expenseDescription);
        mExpenseBookModel.addExpenseBook(expenseBook, isUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    getView().hideProgress();
                    if (isUpdate)
                        getView().onExpenseBookUpdate();
                    else {
                        Bundle expenseBookInfo = new Bundle();
                        expenseBookInfo.putParcelable(Constants.EXTRA_EXPENSE_BOOK, Parcels.wrap(d));
                        getView().addMemberFragment(expenseBookInfo);
                    }
                }, e -> getView().showError(e.getMessage()));
        getView().showCreatingExpenseBookProgress();
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
