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

package com.enfle.spendview.home;

import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.database.api.ExpenseDataAdapter;
import com.enfle.spendview.expense.ExpenseFilter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SummaryActivityPresenter extends BasePresenter<ISummaryActivityView>
        implements MVPPresenter<ISummaryActivityView> {

    ExpenseDataAdapter expenseDataAdapter;

    @Inject
    public SummaryActivityPresenter(ExpenseDataAdapter expenseDataAdapter) {
        this.expenseDataAdapter = expenseDataAdapter;
    }

    public void loadExpenseWithFilters(ExpenseFilter expenseFilter) {
        expenseDataAdapter.getExpensesWithFilters(expenseFilter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(expenses -> getView().showExpenseList(expenses), this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }
}