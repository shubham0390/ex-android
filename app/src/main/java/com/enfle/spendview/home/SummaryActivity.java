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

import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.enfle.spendview.core.mvp.MVPActivity;
import com.enfle.spendview.expense.ExpenseFilter;
import com.enfle.spendview.expense.ExpenseListView;
import com.enfle.spendview.expense.ExpenseListViewModel;
import com.enfle.spendview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SummaryActivity extends MVPActivity<SummaryActivityPresenter> implements ISummaryActivityView {

    @BindView(R.id.time_filter_spinner)
    AppCompatSpinner mTimeFilterSpinner;

    @BindView(R.id.expense_list)
    ExpenseListView mExpenseListView;

    SummaryActivityPresenter mPresenter;

    private ExpenseFilter mExpenseFilter = new ExpenseFilter();

//    private AdapterView.OnItemSelectedListener mTimeFilterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            mExpenseFilter.setTimeFilter(position);
//            mExpenseFilter.setMemberId(UserSettings.getInstance().getUser().getLocalId());
//            mPresenter.loadExpenseWithFilters(mExpenseFilter);
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_summary);
//        mPresenter.subcribe(this);
//        ButterKnife.bind(this);
//        initializeToolbar();
//        toggleHomeBackButton(true);
//        createSpinnerDropDown();
//        mExpenseFilter.setTimeFilter(0);
//        mExpenseFilter.setMemberId(UserSettings.getInstance().getUser().getLocalId());
//        mPresenter.loadExpenseWithFilters(mExpenseFilter);
//    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_summary_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSpinnerDropDown() {

        List<String> list = new ArrayList<>();
        list.add("All");
        list.add("Daily");
        list.add("Monthly");
        list.add("Weekly");
        list.add("Yearly");
        list.add("Custom");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeFilterSpinner.setAdapter(dataAdapter);
       // mTimeFilterSpinner.setOnItemSelectedListener(mTimeFilterItemSelectedListener);
    }

    @Override
    public void showExpenseList(List<ExpenseListViewModel> expenses) {
        if (expenses != null && expenses.size() > 0) {
            mExpenseListView.addData(expenses);
        } else {
            mExpenseListView.showEmptyMessage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubcribe(false);
    }
}
