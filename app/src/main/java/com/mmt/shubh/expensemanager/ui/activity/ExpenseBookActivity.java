package com.mmt.shubh.expensemanager.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.IFragmentDataSharer;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.ExpenseBookListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;

import butterknife.ButterKnife;

public class ExpenseBookActivity extends ToolBarActivity implements IFragmentSwitcher, IFragmentDataSharer {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_book);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        installListFragment();
    }

    private void installListFragment() {
        fragment = new ExpenseBookListFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment, fragment, ExpenseBookListFragment.TAG)
                .addToBackStack(ExpenseBookListFragment.TAG).commit();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        super.injectDependencies(mainComponent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void replaceFragment(String tag, Bundle bundle) {
        /*Fragment fragment = new ExpenseBookFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();*/
    }

    @Override
    public void replaceFragment(int id, Bundle bundle) {

    }

    @Override
    public void removeFragment(String tag, Bundle bundle) {

    }

    @Override
    public void removeFragment(int id, Bundle bundle) {

    }

    @Override
    public void passData(Bundle sharedData) {

    }
}
