package com.km2labs.android.spendview.category;

import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.core.mvp.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 4/7/16.
 */
public class CategoryListFragmentPresenter extends BasePresenter<ICategoryListView> implements MVPPresenter<ICategoryListView> {

    private CategoryDataAdapter mCategoryDataAdapter;

    public CategoryListFragmentPresenter(CategoryDataAdapter categoryDataAdapter) {
        mCategoryDataAdapter = categoryDataAdapter;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadAllCategory() {
        mCategoryDataAdapter.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(expenseCategories -> {
                    getView().setData(expenseCategories);
                }, error -> {
                    getView().showError(error, false);
                });
    }
}
