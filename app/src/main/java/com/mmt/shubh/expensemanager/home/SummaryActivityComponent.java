package com.mmt.shubh.expensemanager.home;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.expense.ExpenseListFragment;
import com.mmt.shubh.expensemanager.expense.ModuleExpenseListFragment;

import dagger.Component;


@Component(
        dependencies = MainComponent.class,
        modules = {
                ModuleExpenseListFragment.class
        }
)
@ActivityScope
public interface SummaryActivityComponent {

    void inject(ExpenseListFragment expenseListFragment);
}
