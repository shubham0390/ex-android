package com.mmt.shubh.expensemanager.category;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;

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
