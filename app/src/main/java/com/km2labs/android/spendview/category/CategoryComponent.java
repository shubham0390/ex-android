package com.km2labs.android.spendview.category;

import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;

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
