package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.MemberExpense;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 7:31 PM
 * TODO:Add class comment.
 */
public interface MemberExpenseDataAdapter extends DataAdapter<MemberExpense> {
    void create(List<MemberExpense> memberExpenses);
}
