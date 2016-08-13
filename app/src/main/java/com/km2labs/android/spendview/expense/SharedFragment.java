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

package com.km2labs.android.spendview.expense;

import android.widget.TextView;

import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.expensebook.ExpenseBookListDialog;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SharedFragment extends MVPFragment<SharedExpenseFragmentPresenter>
        implements SharedExpenseView {

    @BindView(R.id.action_expense_book)
    TextView actionExpenseBook;
    private ExpenseBookListDialog mExpenseBookListDialog;

    private ExpenseBook mExpenseBook;


    @OnClick(R.id.action_expense_book)
    public void onExpenseActionClick() {
        mPresenter.getExpenseBookByMemberId(UserSettings.getInstance().getUserId());
    }

    private void setExpenseBookDetail(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
        actionExpenseBook.setText(expenseBook.getName());
    }

    @Override
    public void onExpenseBookLoad(ExpenseBook expenseBook) {
        setExpenseBookDetail(expenseBook);
    }

    private void setupDefault() {
        mPresenter.getExpenseBook(0);
        setExpenseBookDetail(UserSettings.getInstance().getPersonalExpenseBook());
    }

    @Override
    public void onExpenseBookListLoad(List<ExpenseBook> expenseBookList) {
        mExpenseBookListDialog = new ExpenseBookListDialog(getContext());
        mExpenseBookListDialog.setExpenseBookList(expenseBookList);
        mExpenseBookListDialog.setItemSelectListener(this::setExpenseBookDetail);
        mExpenseBookListDialog.show();
    }


}
