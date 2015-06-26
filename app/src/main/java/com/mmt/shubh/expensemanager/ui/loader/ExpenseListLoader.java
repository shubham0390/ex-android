package com.mmt.shubh.expensemanager.ui.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mmt.shubh.expensemanager.database.content.Expense;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class ExpenseListLoader extends AsyncTaskLoader<List<Expense>> {


    public ExpenseListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Expense> loadInBackground() {
        return null;//ExpenseDataFactory.getExpenseDataAdapter().getAll();
    }
}
