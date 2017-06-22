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

package com.enfle.spendview.expense;

import android.content.Context;

import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.lce.MVPLCEView;
import com.enfle.spendview.database.exception.EmptyDataException;
import com.enfle.spendview.debug.Logger;

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
public class ExpenseListPresenter extends BasePresenter<MVPLCEView<List<ExpenseListViewModel>>>
        implements MVPPresenter<MVPLCEView<List<ExpenseListViewModel>>> {

    protected Context mContext;

    @Inject
    ExpenseModel mExpenseModel;

    private String LOG_TAG = getClass().getName();

    private Observer<List<ExpenseListViewModel>> mExpenseObserver = new Observer<List<ExpenseListViewModel>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.alwaysLog(LOG_TAG, e.getMessage());
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

    @Inject
    public ExpenseListPresenter(ExpenseModel expenseModel, Context context) {
        mContext = context;
        mExpenseModel = expenseModel;
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

    public void loadExpenseWithFilter(ExpenseFilter filter) {
        // TODO: 11/12/2015 remove this after testing
        //filter.setAccountId(1);
        mExpenseModel.loadExpenseWithFilter(filter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mExpenseObserver);
    }
}