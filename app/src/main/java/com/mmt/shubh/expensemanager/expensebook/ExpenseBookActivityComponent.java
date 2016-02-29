package com.mmt.shubh.expensemanager.expensebook;

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
                ExpenseBookListFragmentModule.class,
                ExpenseBookActivityModule.class
        }
)
@ActivityScope
public interface ExpenseBookActivityComponent {

    void inject(ExpenseBookListFragment bookListFragment);

    void inject(ExpenseBookActivity expenseBookActivity);
}
