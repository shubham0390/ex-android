package com.mmt.shubh.expensemanager.expensebook.detail;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 10:49 PM
 * TODO:Add class comment.
 */
@Component(
        dependencies = {
                MainComponent.class
        },
        modules = {
                ExpenseBookActivityModule.class
        }
)
@ActivityScope
public interface ExpenseBookActivityComponent {

    void inject(ExpenseBookActivity expenseBookActivity);

    void inject(ExpenseBookDetailFragment expenseBookDetailFragment);
}
