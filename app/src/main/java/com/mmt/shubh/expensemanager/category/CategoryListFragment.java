package com.mmt.shubh.expensemanager.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.core.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.core.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class CategoryListFragment extends MVPLCEFragment<RecyclerView, List<ExpenseCategory>,
        ICategoryListView, CategoryListFragmentPresenter> implements SearchView.OnQueryTextListener {

    private CategoryAdapter mCategoryAdapter;

    private List<ExpenseCategory> mExpenseCategoryList;

    public CategoryListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(false);
    }

    @Override
    public LCEViewState<List<ExpenseCategory>, ICategoryListView> createViewState() {
        return new LCEViewStateImpl<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_message_emptydata);
    }

    @Override
    public List<ExpenseCategory> getData() {
        return mExpenseCategoryList;
    }

    @Override
    public void setData(List<ExpenseCategory> data) {
        mExpenseCategoryList = data;
        mCategoryAdapter.setData(data);
    }

    private void setupRecyclerView() {
        mContentView.setLayoutManager(new GridLayoutManager(mContentView.getContext(), 4));
        readCategoryFromDB();
        mCategoryAdapter = new CategoryAdapter(getActivity().getApplicationContext());
        mContentView.setAdapter(mCategoryAdapter);
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
        mContentView.scrollToPosition(0);
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

    private void readCategoryFromDB() {
        getData();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadAllCategory();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerCategoryComponent.builder()
                .categoryModule(new CategoryModule())
                .mainComponent(mainComponent)
                .build().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(false);
    }
}
