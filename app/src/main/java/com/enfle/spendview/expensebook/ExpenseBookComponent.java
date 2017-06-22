package com.enfle.spendview.expensebook;

import com.enfle.spendview.expensebook.add.AddMembersFragment;
import com.enfle.spendview.expensebook.add.AddUpdateExpenseBookFragment;

import dagger.Subcomponent;

/**
 * Created by : Subham Tyagi
 * Created on :  20/09/16.
 */

@Subcomponent(modules = ExpensebookModule.class)
public interface ExpenseBookComponent {
    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersFragment addMembersFragment);
}
