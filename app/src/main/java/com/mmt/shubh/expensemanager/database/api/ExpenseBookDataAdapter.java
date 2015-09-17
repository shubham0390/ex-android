package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter extends DataAdapter<ExpenseBook> {

    void addMember(ExpenseBook t);
}
