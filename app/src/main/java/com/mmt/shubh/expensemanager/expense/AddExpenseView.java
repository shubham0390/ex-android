package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.mvp.MVPView;

import java.util.List;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public interface AddExpenseView extends MVPView{
    void onEmptyTitleError();

    void onEmptyAccountError();

    void onEmptyAmountError();

    void onExpenseBookListLoad(List<ExpenseBook> expenseBookList);

    void onAccountListLoad(List<Account> data);

    void onExpenseBookLoad(ExpenseBook expenseBook);
}
