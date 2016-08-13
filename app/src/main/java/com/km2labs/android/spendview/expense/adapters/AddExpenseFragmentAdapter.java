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

package com.km2labs.android.spendview.expense.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.km2labs.android.spendview.expense.AddExpenseFragment;
import com.km2labs.android.spendview.expense.SharedFragment;


public class AddExpenseFragmentAdapter extends FragmentStatePagerAdapter {

    private static final int ITEM_COUNT = 1;

    public AddExpenseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddExpenseFragment();
            case 1:
                return new SharedFragment();
            default:
                throw new IllegalStateException("Invalid Position");
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Personal";
            case 1:
                return "Shared";
            default:
                throw new IllegalStateException("Invalid Id");
        }
    }
}
