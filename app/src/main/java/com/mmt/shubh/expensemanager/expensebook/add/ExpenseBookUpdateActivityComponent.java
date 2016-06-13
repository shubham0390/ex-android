package com.mmt.shubh.expensemanager.expensebook.add;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.expensebook.add.AddMembersToExpenseBookFragment;
import com.mmt.shubh.expensemanager.expensebook.add.AddUpdateExpenseBookFragment;
import com.mmt.shubh.expensemanager.expensebook.add.ModuleExpenseBookUpdate;

import dagger.Component;

/**
 * Created by subhamtyagi on 2/29/16.
 */
@Component(
        dependencies = MainComponent.class,
        modules = {
                ModuleExpenseBookUpdate.class
        })
@ActivityScope
public interface ExpenseBookUpdateActivityComponent {

    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersToExpenseBookFragment bookFragment);
}
