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

package com.enfle.spendview.expensebook;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.enfle.spendview.ItemSelectListener;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.R;

import java.util.List;


public class ExpenseBookListDialog extends AppCompatDialog {

    private ExpenseBookListView mExpenseBookListView;

    private ExpenseBook mSelectedExpenseBook;

    private ItemSelectListener<ExpenseBook> mItemSelectListener;

    public ExpenseBookListDialog(Context context) {
        super(context);
        init(context);
    }

    public ExpenseBookListDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected ExpenseBookListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_expense_book_list);
        setTitle(R.string.select_expense_book);
        mExpenseBookListView = (ExpenseBookListView) findViewById(R.id.expense_book_list);
    }


    public void setExpenseBookList(List<ExpenseBook> expenseBookList) {
        if (expenseBookList.size() <= 0) {
            mExpenseBookListView.showEmptyText(R.string.empty_expense_book);
        } else {
            mExpenseBookListView.setOnItemClickListener((parent, view, position, id) -> {
                mSelectedExpenseBook = mExpenseBookListView.getItemAtPosition(position);
                mItemSelectListener.onItemSelect(mSelectedExpenseBook);
                dismiss();
                return true;
            });
            mExpenseBookListView.addData(expenseBookList);
        }
    }

    public void setItemSelectListener(ItemSelectListener<ExpenseBook> mItemSelectListener) {
        this.mItemSelectListener = mItemSelectListener;
    }

    public ExpenseBook getSelectedItem() {
        return mSelectedExpenseBook;
    }
}
