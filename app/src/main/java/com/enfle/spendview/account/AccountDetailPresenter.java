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

package com.enfle.spendview.account;

import com.enfle.spendview.core.Selection;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.database.content.contract.ExpenseContract;
import com.enfle.spendview.expense.ExpenseListViewModel;
import com.enfle.spendview.utils.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountDetailPresenter extends BasePresenter<IAccountDetailView> implements MVPPresenter<IAccountDetailView> {


    AccountModel mAccountModel;

    @Inject
    public AccountDetailPresenter(AccountModel accountModel) {
        mAccountModel = accountModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadExpenseByAccountId(long id) {
        long currentYearTimeInMilli = 0l;
        Selection.Builder builder = new Selection.Builder();
        builder.appendSelection(ExpenseContract.ACCOUNT_KEY, Selection.EQUAL, id)
                .appendOperation(Selection.AND)
                .appendSelection(ExpenseContract.EXPENSE_DATE, Selection.GREATER, currentYearTimeInMilli);

        mAccountModel.getExpenses(builder.build())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(d -> {
                    getView().showExpense(d);
                    createGraphData(d);
                },
                e -> getView().showError(e, false));
    }

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
        getView().setData(mapData);
    }
}
