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

package com.enfle.spendview.expensebook.add;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enfle.spendview.core.mvp.MVPFragment;
import com.enfle.spendview.core.view.DotsView;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.utils.Constants;
import com.enfle.spendview.utils.RxEventBus;
import com.enfle.spendview.utils.Utilities;
import com.enfle.spendview.R;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Umang Chamaria
 *         TODO : save image uri and expense book name in database
 */
public class AddUpdateExpenseBookFragment extends MVPFragment<AddExpensebookFragmentContract.Presenter> implements AddExpensebookFragmentContract.View {

    @BindView(R.id.new_expense_book_name)
    EditText mExpenseName;

    @BindView(R.id.new_expense_book_description)
    EditText mExpenseDescription;

    AlertDialog mProgressDialog;
    private boolean isUpdate;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            ExpenseBook expenseBook = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_EXPENSE_BOOK));
            mExpenseName.setText(expenseBook.getName());
            mExpenseDescription.setText(expenseBook.getDescription());
        }
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_add_expense_book, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_new_expense_book, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem update = menu.findItem(R.id.action_update);
        update.setVisible(isUpdate);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_next:
                Utilities.hideKeyboard(getActivity());
                mPresenter.validateExpenseNameAndProceed(mExpenseName.getText().toString(), mExpenseDescription.getText().toString(), isUpdate);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEmptyError() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .error_empty_expense_book_name),
                Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void onDuplicateExpenseBook() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .error_expense_book_already_exists),
                Toast.LENGTH_LONG).show();
        mExpenseName.requestFocus();
    }

    @Override
    public void onExpenseBookAdded(ExpenseBook expenseBook) {
        RxEventBus.getInstance().post(expenseBook);
    }

    @Override
    public void onProgressUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        mProgressDialog = builder.create();
        mProgressDialog.setContentView(R.layout.dialog_progress);
        mProgressDialog.show();
        DotsView dotsView = (DotsView) mProgressDialog.findViewById(R.id.progress_bar);
        dotsView.start();
        TextView textView = (TextView) mProgressDialog.findViewById(R.id.message);
        textView.setText("Creating Expense Book ...");

    }

    @Override
    public void onStopProgress() {
        mProgressDialog.cancel();
    }
}

