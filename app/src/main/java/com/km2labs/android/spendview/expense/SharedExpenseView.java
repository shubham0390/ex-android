package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.core.mvp.MVPView;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

/**
 * Created by Subham on 19/05/16.
 */
public interface SharedExpenseView extends MVPView {

    void onExpenseBookListLoad(List<ExpenseBook> expenseBookList);

    void onExpenseBookLoad(ExpenseBook expenseBook);
}
