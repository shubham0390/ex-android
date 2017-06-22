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

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.enfle.spendview.core.mvp.MVPActivity;
import com.enfle.spendview.expense.adapters.AddExpenseFragmentAdapter;
import com.enfle.spendview.R;

import butterknife.BindView;

public class AddExpenseActivity extends MVPActivity<AddExpensePresenter> {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setTitle(R.string.add_new_expense);
        setup();

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    private void setup() {
        AddExpenseFragmentAdapter adapter = new AddExpenseFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
