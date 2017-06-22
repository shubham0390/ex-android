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

package com.enfle.spendview.expensebook.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:06 PM
 * TODO:Add class comment.
 */
public class ExpenseBookFragmentAdapter extends FragmentStatePagerAdapter {

    List<ExpenseBook> mExpenseBooks = new ArrayList<>();

    public ExpenseBookFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ExpenseBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_EXPENSE_BOOK_ID, mExpenseBooks.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mExpenseBooks.get(position).getName();
    }

    @Override
    public int getCount() {
        return mExpenseBooks.size();
    }

    public void addData(List<ExpenseBook> data) {
        mExpenseBooks.addAll(data);
        notifyDataSetChanged();
    }


    public ExpenseBook getDataAtPosition(int position) {
        return mExpenseBooks.get(position);
    }
}
