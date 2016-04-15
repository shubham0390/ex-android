package com.mmt.shubh.expensemanager.category;

import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 4/7/16.
 */
public class CategoryListFragmentPresenter extends MVPAbstractPresenter<ICategoryListView>
        implements MVPPresenter<ICategoryListView> {

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
