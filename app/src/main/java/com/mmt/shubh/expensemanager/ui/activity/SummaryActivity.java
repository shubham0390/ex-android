package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.activity.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseFilter;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SummaryActivity extends ToolBarActivity {

    @Bind(R.id.time_filter_spinner)
    AppCompatSpinner mTimeFilterSpinner;

    private ExpenseListFragment mExpenseListFragment;

    private ExpenseFilter mExpenseFilter = new ExpenseFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        createSpinnerDropDown();
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

    //Add animals into spinner dynamically
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

    private AdapterView.OnItemSelectedListener mTimeFilterItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mExpenseFilter.setTimeFilter(position+1);
            mExpenseFilter.setMemberId(1);
            mExpenseListFragment = (ExpenseListFragment) getSupportFragmentManager().findFragmentByTag("ExpenseList");
            mExpenseListFragment.applyExpenseFilter(mExpenseFilter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
