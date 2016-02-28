package com.mmt.shubh.expensemanager.category;

import com.mmt.shubh.expensemanager.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;

/**
 * Created by Subham Tyagi,
 * on 14/Sep/2015,
 * 1:29 PM
 * TODO:Add class comment.
 */
public class CategoryGraphFragment extends MVPFragment<MVPLCEView<CategoryModel>, CategoryGraphFragmentPresenter> implements MVPLCEView<CategoryModel> {

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(CategoryModel data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
