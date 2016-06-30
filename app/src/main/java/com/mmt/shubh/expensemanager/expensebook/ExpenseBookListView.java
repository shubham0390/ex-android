package com.mmt.shubh.expensemanager.expensebook;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseBookListView extends LinearLayout {

    public static final int MODE_EXPENSE_BOOK = 0;
    public static final int MODE_MEMBER = 1;
    public static final int MODE_SUMMARY = 2;
    public static final int MODE_DIALOG = 3;

    ExpenseBookListAdapter mExpenseBookListAdapter;

    TextView mEmptyText;

    TextView mMoreTextView;

    ProgressBar mProgressBar;

    View mProgressContainer;
    ListRecyclerView listRecyclerView;

    List<ExpenseBook> mExpenseBooks;
    boolean isExpended;

    int mMode;
    private ListRecyclerView.OnItemClickListener mItemClickListener = (parent, view, position, id) -> {
        mExpenseBookListAdapter.getItem(position);
        return false;
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
        LayoutInflater.from(context).inflate(R.layout.expense_book_list_view, this, true);
        listRecyclerView = (ListRecyclerView) findViewById(R.id.list);
        mProgressContainer = findViewById(R.id.progress_container);
        mEmptyText = (TextView) findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mMoreTextView = (TextView) findViewById(R.id.more);
        mExpenseBookListAdapter = new ExpenseBookListAdapter();
        if (isInEditMode()) {
            return;
        }
        listRecyclerView.setAdapter(mExpenseBookListAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);

        mMoreTextView.setOnClickListener(v -> {
            if (isExpended) {
                collapseList();
            } else {
                expendList();
            }
        });
    }

    private void expendList() {
        mMoreTextView.setText("Less");
        isExpended = true;
        mExpenseBookListAdapter.clear();
        mExpenseBookListAdapter.addData(mExpenseBooks);
    }

    private void collapseList() {
        mMoreTextView.setText("More");
        isExpended = false;
        List<ExpenseBook> expenseBooks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            expenseBooks.add(mExpenseBooks.get(i));
        }
        mExpenseBookListAdapter.clear();
        mExpenseBookListAdapter.addData(expenseBooks);
    }


    public void setMode(int mode) {
        mMode = mode;
    }

    public void addData(List<ExpenseBook> expenseBooks) {
        mExpenseBookListAdapter.setMode(mMode);
        mProgressContainer.setVisibility(GONE);
        mExpenseBooks = expenseBooks;
        if (expenseBooks.size() <= 2 || mMode == MODE_DIALOG) {
            mExpenseBookListAdapter.addData(expenseBooks);
            mMoreTextView.setVisibility(GONE);
        } else {
            collapseList();
        }
    }

    public void showEmptyText(@StringRes int id) {
        mEmptyText.setVisibility(VISIBLE);
        mEmptyText.setText(id);
        mProgressBar.setVisibility(GONE);
    }

    public void setOnItemClickListener(ListRecyclerView.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public ExpenseBook getItemAtPosition(int position) {
        return mExpenseBookListAdapter.getItem(position);
    }

}
