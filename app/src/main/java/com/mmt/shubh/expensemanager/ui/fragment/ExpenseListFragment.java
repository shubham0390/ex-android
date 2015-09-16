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

package com.mmt.shubh.expensemanager.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.ui.adapters.ExpenseListAdapter;
import com.mmt.shubh.expensemanager.ui.fragment.base.BaseFragment;
import com.mmt.shubh.expensemanager.ui.view.DividerItemDecoration;
import com.mmt.shubh.expensemanager.ui.view.ScrollingLinearLayoutManager;

/**
 * Created by Subham Tyagi,
 * on 16/Jun/2015,
 * 6:37 PM
 * TODO:Add class comment.
 */
public class ExpenseListFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener, ExpenseListAdapter.ListItemClickListener {

    private static final int LOADER_ID = 2;

    private ExpenseListAdapter mExpensesAdapter;

    private RecyclerView mExpenseList;
    private ContentLoadingProgressBar mBar;

    private TextView mEmpytTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        mExpenseList = (RecyclerView) view.findViewById(R.id.expense_list);
        mBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress_bar);
        mEmpytTextView = (TextView) view.findViewById(R.id.empty_text);
        final LinearLayoutManager layoutManager = new ScrollingLinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false, 1000);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mExpenseList.setLayoutManager(layoutManager);
        mExpenseList.addItemDecoration(
                new DividerItemDecoration(getResources().getDrawable(R.drawable.list_devider_line), true, true));
        mExpensesAdapter = new ExpenseListAdapter(null, this);
        mExpenseList.setAdapter(mExpensesAdapter);
        mBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, LOADER_CALLBACK);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_expenselist, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onGlobalLayout() {
        final int visibility = mBar.getVisibility();
        /*if (mLastVisibility != visibility) {
            if (visibility == View.VISIBLE) {
                mVisibilityChangedTime = System.currentTimeMillis();
                mShowTextDone.setText("Shown at "
                        + (mVisibilityChangedTime - mShowTime));
            } else {
                mHideTextDone.setText("Hidden after "
                        + (System.currentTimeMillis() - mVisibilityChangedTime));
            }
            mLastVisibility = mBar.getVisibility();
        }*/
    }

    @Override
    public void onItemClickListener(Cursor cursor) {

    }

    static class ExpenseLoader extends CursorLoader {

        public ExpenseLoader(Context context) {
            super(context, ExpenseContract.EXPENSE_URI, null, null, null, null);
        }

        @Override
        public Cursor loadInBackground() {
            return super.loadInBackground();
        }

    }

    private LoaderCallbacks<Cursor> LOADER_CALLBACK = new LoaderCallbacks<Cursor>() {

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mBar.setVisibility(View.GONE);
            if (data != null) {
                mExpensesAdapter.swapCursor(data);
                if (data.getCount() > 0) {
                    mEmpytTextView.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new ExpenseLoader(getActivity().getApplicationContext());
        }

        /**
         * Determines whether or not the list is empty, but we're still potentially loading data.
         * This represents an ambiguous state where we may not want to show "No messages", since
         * it may still just be loading.
         */
        private boolean isEmptyAndLoading(Cursor cursor) {
            return cursor.getCount() == 0;
        }
    };
}
