package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;

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
