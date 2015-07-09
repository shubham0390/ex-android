package com.mmt.shubh.expensemanager.ui.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;
import com.mmt.shubh.expensemanager.ui.models.ExpenseBookMemberDetailActivity;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 28/Jun/2015,
 * 11:16 AM
 * TODO:Add class comment.
 */
public class DemoLoader extends AsyncTaskLoader<List<ExpenseBookMemberDetailActivity>> {
    long memberId;
    long expenseBookId;

    public DemoLoader(Context context) {
        super(context);
    }

    public DemoLoader(Context context, long expenseBookId, long memberId) {
        super(context);
        this.expenseBookId = expenseBookId;
        this.memberId = memberId;
    }

    @Override
    public List<ExpenseBookMemberDetailActivity> loadInBackground() {
        //ExpenseSqlDataAdapter sqlDataAdapter = new ExpenseSqlDataAdapter();
       // sqlDataAdapter.getAllExpenseForMemberAndExpenseBookGroupByDate(getContext(), expenseBookId, memberId);
        return null;
    }


}
