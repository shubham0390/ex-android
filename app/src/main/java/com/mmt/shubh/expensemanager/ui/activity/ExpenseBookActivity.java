package com.mmt.shubh.expensemanager.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.fragment.ExistingExpenseBookFragment;

public class ExpenseBookActivity extends AppCompatActivity {

    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragment = new ExistingExpenseBookFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();

    }
}
