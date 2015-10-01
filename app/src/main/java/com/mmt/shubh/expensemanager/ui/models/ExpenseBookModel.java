package com.mmt.shubh.expensemanager.ui.models;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;

/**
 * Created by Subham Tyagi,
 * on 01/Oct/2015,
 * 10:08 AM
 * TODO:Add class comment.
 */
public class ExpenseBookModel {

    private ExpenseBookDataAdapter mExpenseBookDataAdapter;

    private MemberDataAdapter mMemberDataAdapter;

    public ExpenseBookModel(ExpenseBookDataAdapter dataAdapter) {
        mExpenseBookDataAdapter = dataAdapter;
    }
}
