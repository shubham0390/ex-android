package com.mmt.shubh.expensemanager.home;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.expense.ExpenseFilter;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.settings.UserSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends ToolBarActivity implements ISummaryActivityView {

    @BindView(R.id.time_filter_spinner)
    AppCompatSpinner mTimeFilterSpinner;

    @BindView(R.id.expense_list)
    ExpenseListView mExpenseListView;
    @Inject
    SummaryActivityPresenter mPresenter;
    private ExpenseFilter mExpenseFilter = new ExpenseFilter();
    private AdapterView.OnItemSelectedListener mTimeFilterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mExpenseFilter.setTimeFilter(position);
            mExpenseFilter.setMemberId(UserSettings.getInstance().getUserInfo().getId());
            mPresenter.loadExpenseWithFilters(mExpenseFilter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        mPresenter.attachView(this);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        createSpinnerDropDown();
        mExpenseFilter.setTimeFilter(0);
        mExpenseFilter.setMemberId(UserSettings.getInstance().getUserInfo().getId());
        mPresenter.loadExpenseWithFilters(mExpenseFilter);
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
        mTimeFilterSpinner.setOnItemSelectedListener(mTimeFilterItemSelectedListener);
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
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerSummaryActivityComponent.builder()
                .mainComponent(mainComponent)
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(false);
    }
}
