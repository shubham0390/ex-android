/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mmt.shubh.expensemanager.expense;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.expense.adapters.ExpenseListAdapter;
import com.mmt.shubh.expensemanager.home.DaggerSummaryActivityComponent;
import com.mmt.shubh.expensemanager.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.mvp.lce.SupportMVPLCEFragment;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 16/Jun/2015,
 * 6:37 PM
 * TODO:Add class comment.
 */
public class ExpenseListFragment extends SupportMVPLCEFragment<ListRecyclerView, List<ExpenseListViewModel>,
        MVPLCEView<List<ExpenseListViewModel>>, ExpenseListPresenter> implements MVPLCEView<List<ExpenseListViewModel>> {

    private ExpenseListAdapter mExpensesAdapter;

    private List<ExpenseListViewModel> mExpenseListViewModels;

    private ExpenseFilter mExpenseFilter = new ExpenseFilter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public LCEViewState<List<ExpenseListViewModel>, MVPLCEView<List<ExpenseListViewModel>>> createViewState() {
        return new LCEViewStateImpl<>();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_expense_list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mExpensesAdapter = new ExpenseListAdapter(mContentView,ExpenseListView.MODE_SUMMARY);
        mContentView.setAdapter(mExpensesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_expenselist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                // TODO: 11/11/2015 handle expense sort
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Unable to load Expenses";
    }


    @Override
    public List<ExpenseListViewModel> getData() {
        return mExpenseListViewModels;
    }

    @Override
    public void setData(List<ExpenseListViewModel> data) {
        mExpenseListViewModels = data;
        showContent();
        mExpensesAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadExpenseForMember(1);
    }

    public void applyExpenseFilter(ExpenseFilter filter) {
        mExpenseFilter = filter;
        mPresenter.loadExpenseWithFilter(mExpenseFilter);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerSummaryActivityComponent.builder()
                .mainComponent(mainComponent)
                //.moduleExpenseListFragment(new ModuleExpenseListFragment())
                .build()
        //        .inject(this)
        ;
    }
}
