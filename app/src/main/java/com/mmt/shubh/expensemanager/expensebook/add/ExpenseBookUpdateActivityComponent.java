package com.mmt.shubh.expensemanager.expensebook.add;

import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;

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
