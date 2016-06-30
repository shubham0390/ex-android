package com.mmt.shubh.expensemanager.expensebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.mmt.shubh.expensemanager.R;


public class ExpenseBookOverviewView extends RelativeLayout {

    public ExpenseBookOverviewView(Context context) {
        super(context);
    }

    public ExpenseBookOverviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpenseBookOverviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpenseBookOverviewView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.expense_book_overview_view, this, true);
    }
}
