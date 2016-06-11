package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.mvp.MVPView;

import java.util.List;

/**
 * Created by Subham on 19/05/16.
 */
public interface SharedExpenseView extends MVPView {

    void onExpenseBookListLoad(List<ExpenseBook> expenseBookList);

    void onExpenseBookLoad(ExpenseBook expenseBook);
}
