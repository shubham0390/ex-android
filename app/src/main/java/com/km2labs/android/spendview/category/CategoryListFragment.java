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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.km2labs.android.spendview.core.base.RecyclerViewFragment;
import com.km2labs.android.spendview.database.content.ExpenseCategory;
import com.km2labs.expenseview.android.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryListFragment extends RecyclerViewFragment implements SearchView.OnQueryTextListener {

    private CategoryAdapter mCategoryAdapter;

    private List<ExpenseCategory> mExpenseCategoryList;

    public CategoryListFragment() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_category, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ExpenseCategory> filteredCategoryList = filter(mExpenseCategoryList, query);
        mCategoryAdapter.animateTo(filteredCategoryList);
        return true;
    }

    /**
     * returns a list ExpenseCategory which match the query string
     *
     * @param expenseCategoryList list of contacts to be searched in
     * @param query               string to be searched in
     * @return list of contacts containing the query string
     */
    @Nullable
    private List<ExpenseCategory> filter(List<ExpenseCategory> expenseCategoryList, String query) {
        query = query.toLowerCase();
        List<ExpenseCategory> filteredCategory = new ArrayList<>();
        for (ExpenseCategory category : expenseCategoryList) {
            final String name = category.getCategoryName().toLowerCase();
            if (name.contains(query)) {
                filteredCategory.add(category);
            }
        }
        return filteredCategory;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected LayoutManagerType getLayoutManagerType() {
        return null;
    }

    @Override
    protected void loadList() {

    }
}
