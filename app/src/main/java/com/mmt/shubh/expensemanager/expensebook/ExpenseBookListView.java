package com.mmt.shubh.expensemanager.expensebook;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expense.ExpenseListAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import java.util.List;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class ExpenseBookListView extends FrameLayout {

    public static final int MODE_ACCOUNT = 0;
    public static final int MODE_EXPENSE_BOOK = 1;
    public static final int MODE_MEMBER = 2;
    public static final int MODE_SUMMARY = 3;

    ExpenseBookListAdapter mExpenseListAdapter;

    int mMode;
    private ListRecyclerView.OnItemClickListener mItemClickListener = new ListRecyclerView.OnItemClickListener() {
        @Override
        public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
            // TODO: 2/20/16 Open expense detail view from here
            return false;
        }
    };

    public ExpenseBookListView(Context context) {
        super(context);
        init(context);
    }

    public ExpenseBookListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpenseBookListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpenseBookListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        ListRecyclerView listRecyclerView = new ListRecyclerView(context);
        addView(listRecyclerView);
        mExpenseListAdapter = new ExpenseBookListAdapter();
        listRecyclerView.setAdapter(mExpenseListAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public void addData(List<ExpenseBook> expenseBooks) {
        mExpenseListAdapter.addData(expenseBooks);
    }
}
