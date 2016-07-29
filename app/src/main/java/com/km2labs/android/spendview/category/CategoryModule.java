package com.km2labs.android.spendview.category;

import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 4/8/16.
 */
@Module
public class CategoryModule {

    @Provides
    @ActivityScope
    public CategoryListFragmentPresenter provideCategoryListFragmentPresenter(CategoryDataAdapter categoryDataAdapter) {
        return new CategoryListFragmentPresenter(categoryDataAdapter);
    }
}
