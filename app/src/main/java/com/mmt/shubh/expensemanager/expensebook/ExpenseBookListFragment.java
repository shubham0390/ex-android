package com.mmt.shubh.expensemanager.expensebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.RecyclerViewFragment;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import org.parceler.Parcels;

import java.util.List;

public class ExpenseBookListFragment extends RecyclerViewFragment<MVPLCEView<List<ExpenseBook>>, ExpenseBookListPresenter>
        implements MVPLCEView<List<ExpenseBook>> {

    public static final String TAG = "ExpenseBookListFragment";

    ExpenseBookListAdapter mExpenseBookListAdapter;

    private long selectedExpenseBookId;

    public ExpenseBookListFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_existing_expense_book;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(false);
        mExpenseBookListAdapter = new ExpenseBookListAdapter();
        ListRecyclerView recyclerView = (ListRecyclerView) mRecyclerView;

        recyclerView.setOnItemClickListener((parent, view, position, id) -> {
            selectedExpenseBookId = id;
            ExpenseBookListFragment.this.installExpenseBookDetail(position);
            return true;
        });

        recyclerView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedExpenseBookId = id;
            ExpenseBookListFragment.this.showConfirmationDialog();
            return true;
        });
    }

    private void deleteExpenseBook() {
        mPresenter.deleteExpenseBook(selectedExpenseBookId);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setTitle(R.string.delete_expense_book_dialog_title);
        builder.setMessage(R.string.delete_expense_book_dialog_message);

        builder.setPositiveButton(R.string.deleted_key, (dialog, which) -> {
            dialog.dismiss();
            deleteExpenseBook();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });
    }

    private void installExpenseBookDetail(int position) {
        ExpenseBook expenseBook = mExpenseBookListAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_EXPENSE_BOOK, Parcels.wrap(expenseBook));
        Intent intent = new Intent(getActivity(), ExpenseBookDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_expense_book, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_expense) {

            Intent intent = new Intent(getActivity().getApplicationContext(), ExpenseBookAddUpdateActivity.class);
            startActivityForResult(intent, 123);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        showRecylerView(false);
    }

    @Override
    public void showContent() {
        showRecylerView(true);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        // TODO: 9/11/2015 handle this case if required
    }

    @Override
    public void setData(List<ExpenseBook> data) {
        if (data == null || data.size() <= 0) {
            showRecylerView(false);
        } else {
            mExpenseBookListAdapter.addData(data);
            showRecylerView(true);
            setAdapter(mExpenseBookListAdapter);
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadExpenseBookList();
    }


    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        super.injectDependencies(mainComponent);
        ExpenseBookActivityComponent component = DaggerExpenseBookActivityComponent.builder()
                .expenseBookListFragmentModule(new ExpenseBookListFragmentModule())
                .mainComponent(mainComponent)
                .build();
        component.inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData(false);
    }
}
