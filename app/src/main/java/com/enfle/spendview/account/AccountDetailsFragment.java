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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enfle.spendview.core.mvp.MVPFragment;
import com.enfle.spendview.database.content.Account;
import com.enfle.spendview.expense.ExpenseListView;
import com.enfle.spendview.expense.ExpenseListViewModel;
import com.enfle.spendview.utils.Constants;
import com.enfle.spendview.R;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 12/Jul/2015,
 * 10:38 PM
 * TODO:Add class comment.
 */
public class AccountDetailsFragment extends MVPFragment<AccountDetailPresenter> implements IAccountDetailView {

    @BindView(R.id.expense_list)
    ExpenseListView mExpenseListView;

    Account mAccount;

    @Inject
    AccountDetailPresenter mAccountDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccount = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_ACCOUNT));
    }

    @Override
    protected View getFragmentView(@NonNull LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountDetailPresenter.loadExpenseByAccountId(mAccount.getId());
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        Timber.e("Unable to fetch data %s", e.getMessage());
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mAccountDetailPresenter.loadExpenseByAccountId(mAccount.getId());
    }

    @Override
    public void showExpense(List<ExpenseListViewModel> listViewModels) {
        mExpenseListView.addData(listViewModels);
    }


    private void addMnth(List<String> strings) {
        strings.add("Jan");
        strings.add("Feb");
        strings.add("Mar");
        strings.add("Apr");
        strings.add("May");
        strings.add("Jun");
        strings.add("Jul");
        strings.add("Aug");
        strings.add("Sep");
        strings.add("Oct");
        strings.add("Nov");
        strings.add("Dec");
    }
}
