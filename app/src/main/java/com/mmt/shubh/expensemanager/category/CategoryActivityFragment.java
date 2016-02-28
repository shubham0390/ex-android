package com.mmt.shubh.expensemanager.category;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.category.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryActivityFragment extends Fragment implements SearchView.OnQueryTextListener{

    private final String TAG = getClass().getSimpleName();

    @Bind(R.id.category_list)
    RecyclerView mCategoryList;

    private CategoryAdapter mCategoryAdapter;

    private List<ExpenseCategory> mExpenseCategoryList;

    public CategoryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void setupRecyclerView() {
        mCategoryList.setLayoutManager(new GridLayoutManager(mCategoryList.getContext(),4));
        readCategoryFromDB();
        mCategoryAdapter = new CategoryAdapter(mExpenseCategoryList, getActivity().getApplicationContext());
        mCategoryList.setAdapter(mCategoryAdapter);
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
        mCategoryList.scrollToPosition(0);
        return true;
    }

    /**
     * returns a list ExpenseCategory which match the query string
     * @param expenseCategoryList list of contacts to be searched in
     * @param query       string to be searched in
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

    private void readCategoryFromDB(){
        mExpenseCategoryList = new ArrayList<>();
            Cursor cursor = getActivity().getContentResolver().query(CategoryContract.CATEGORY_URI,
                    new String[]{CategoryContract.CATEGORY_NAME, CategoryContract
                            .CATEGORY_IMAGE_NAME}, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(cursor.getColumnIndex(CategoryContract

                                .CATEGORY_NAME));
                        String categoryImageName = cursor.getString(cursor.getColumnIndex
                                (CategoryContract.CATEGORY_IMAGE_NAME));
                        mExpenseCategoryList.add(new ExpenseCategory(categoryName, categoryImageName));
                    } while (cursor.moveToNext());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

    }
}
