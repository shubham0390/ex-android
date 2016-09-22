package com.km2labs.android.spendview.expensebook;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.expensebook.add.AddMembersFragment;
import com.km2labs.android.spendview.expensebook.add.AddUpdateExpenseBookFragment;

import dagger.Subcomponent;

/**
 * Created by : Subham Tyagi
 * Created on :  20/09/16.
 */

@Subcomponent(modules = ExpensebookModule.class)
@ConfigPersistent
public interface ExpenseBookComponent {
    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersFragment addMembersFragment);
}
