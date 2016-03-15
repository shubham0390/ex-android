package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.mvp.MVPView;

/**
 * Created by subhamtyagi on 3/12/16.
 */
public interface AddExpenseView extends MVPView{
    void showEmptyTitleError();

    void showEmptyAccountError();

    void showEmptyAmountError();
}
