package com.mmt.shubh.expensemanager.ui.component;

import com.mmt.shubh.expensemanager.dagger.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.ui.activity.ExpenseBookActivity;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.ExpenseBookListFragment;
import com.mmt.shubh.expensemanager.ui.module.ExpenseBookActivityModule;
import com.mmt.shubh.expensemanager.ui.module.ExpenseBookListFragmentModule;

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

    //ExpenseBookDataAdapter<ExpenseBook> getExpenseBookDataAdapter(Context context);
}
