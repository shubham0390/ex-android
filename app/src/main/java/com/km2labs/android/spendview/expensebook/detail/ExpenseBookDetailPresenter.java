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

package com.km2labs.android.spendview.expensebook.detail;


import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.exception.EmptyDataException;
import com.km2labs.android.spendview.expense.ExpenseFilter;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.expense.ExpenseModel;
import com.km2labs.android.spendview.utils.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class ExpenseBookDetailPresenter extends BasePresenter<ExpenseBookDetailView> implements
        MVPPresenter<ExpenseBookDetailView> {


    @Inject
    ExpenseModel mExpenseModel;

    @Inject
    public ExpenseBookDetailPresenter(ExpenseModel expenseModel) {
        mExpenseModel = expenseModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseByExpenseBookId(long expenseBoolId) {
        ExpenseFilter expenseFilter = new ExpenseFilter();
        expenseFilter.setExpenseBookId(expenseBoolId);
        expenseFilter.setTimeFilter(ExpenseFilter.TIME_FILTER_YEAR);
        mExpenseModel.loadExpenseWithFilter(expenseFilter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mExpenseObserver);
    }

    private Observer<List<ExpenseListViewModel>> mExpenseObserver = new Observer<List<ExpenseListViewModel>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e.getMessage());
            e.printStackTrace();
            getView().showError(e, false);
        }

        @Override
        public void onNext(List<ExpenseListViewModel> expenses) {
            if (expenses == null || expenses.isEmpty()) {
                getView().showError(new EmptyDataException("No Expense Present"), false);
            } else
            createGraphData(expenses);
                getView().setData(expenses);
        }
    };
    private void createGraphData(List<ExpenseListViewModel> listViewModels) {
        Map<Integer, Double> mapData = new HashMap<>();
        for (ExpenseListViewModel expenseListViewModel : listViewModels) {

            long expenseTime = expenseListViewModel.getExpenseDateInMill();

            Double expenseAmount = expenseListViewModel.getExpenseAmount();
            Integer mnth = DateUtil.getMonthCount(expenseTime);

            if (mapData.containsKey(mnth)) {
                Double amount = mapData.get(mnth);
                amount += expenseAmount;
                mapData.put(mnth, amount);
            } else {
                mapData.put(mnth, expenseAmount);
            }
        }
        getView().setGraphData(mapData);
    }

}
