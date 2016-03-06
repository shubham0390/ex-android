package com.mmt.shubh.expensemanager.expensebook;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by subhamtyagi on 2/29/16.
 */
@Component(
        dependencies = MainComponent.class,
        modules = {
                ModuleExpneseBookUpdate.class
        })
@ActivityScope
public interface ExpenseBookUpdateActivityComponent {

    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersToExpenseBookFragment bookFragment);
}
