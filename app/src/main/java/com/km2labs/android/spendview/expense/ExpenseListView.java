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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.km2labs.android.spendview.core.recyclerview.ListRecyclerView;
import com.km2labs.android.spendview.expense.adapters.ExpenseListAdapter;

import java.util.List;

public class ExpenseListView extends FrameLayout {

    public static final int MODE_ACCOUNT = 0;
    public static final int MODE_EXPENSE_BOOK = 1;
    public static final int MODE_MEMBER = 2;
    public static final int MODE_SUMMARY = 3;

    ExpenseListAdapter mExpenseListAdapter;

    int mMode = MODE_MEMBER;
    private ListRecyclerView.OnItemClickListener mItemClickListener = (parent, view, position, id) -> {
        // TODO: 2/20/16 Open expense detail view from here
        return false;
    };

    public ExpenseListView(Context context) {
        super(context);
        init(context);
    }

    public ExpenseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpenseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpenseListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        ListRecyclerView listRecyclerView = new ListRecyclerView(context);
        addView(listRecyclerView);
        if (isInEditMode()) {
            return;
        }
        mExpenseListAdapter = new ExpenseListAdapter(listRecyclerView, mMode);
        listRecyclerView.setAdapter(mExpenseListAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public void setMode(int mode) {
        mMode = mode;
        mExpenseListAdapter.setMode(mode);
    }

    public void addData(List<ExpenseListViewModel> listViewModels) {
        mExpenseListAdapter.setData(listViewModels);
    }

    public void showEmptyMessage() {


    }
}
