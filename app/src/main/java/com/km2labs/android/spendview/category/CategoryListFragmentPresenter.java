/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
