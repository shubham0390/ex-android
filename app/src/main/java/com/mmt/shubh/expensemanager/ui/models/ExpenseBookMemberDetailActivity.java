package com.mmt.shubh.expensemanager.ui.models;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

import org.parceler.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by Subham Tyagi,
 * on 28/Jun/2015,
 * 11:13 AM
 * TODO:Add class comment.
 */
public class ExpenseBookMemberDetailActivity {

    private ExpenseBook mExpenseBook;
    Map<String, Long> mStringMap = new HashedMap();

    public ExpenseBook getExpenseBook() {
        return mExpenseBook;
    }

    public void setExpenseBook(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
    }

    public void Add(String s, Long l) {
        mStringMap.put(s, l);
    }

    public Map<String, Long> getStringMap() {
        return mStringMap;
    }
}
