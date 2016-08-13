/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.expensebook.add;

import android.os.Bundle;
import android.text.TextUtils;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.expensebook.ExpenseBookModel;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.android.spendview.database.content.ExpenseBook;

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
