package com.mmt.shubh.expensemanager.category;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by subhamtyagi on 4/8/16.
 */
@Component(dependencies = {
        MainComponent.class
}, modules = {
        CategoryModule.class

})
@ActivityScope
public interface CategoryComponent {

    void inject(CategoryListFragment categoryListFragment);
}
