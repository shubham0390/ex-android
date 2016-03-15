package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;

import dagger.Component;

/**
 * Created by subhamtyagi on 3/12/16.
 */
@Component(dependencies = {
        MainComponent.class
},
        modules = {
        AddExpenseFragmentModule.class
})
@ActivityScope
public interface AddExpenseActivityComponent {

    void inject(AddExpenseFragment addExpenseFragment);

}
