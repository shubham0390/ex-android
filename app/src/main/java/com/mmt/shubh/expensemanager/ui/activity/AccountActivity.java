package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.activity.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.ui.fragment.account.AccountListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.account.AddEditAccountFragment;
import com.mmt.shubh.expensemanager.ui.listener.AccountFragmentIntractionListener;

import butterknife.ButterKnife;


public class AccountActivity extends ToolBarActivity implements AccountFragmentIntractionListener {

    public static final int MODE_ADD = 0;
    public static final int MODE_LIST = 1;
    public static final int MODE_VIEW = 2;

    private int mCurrentMode;

    private android.app.Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        setTitle(R.string.account);

        onFragmentIntraction(MODE_LIST, null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
            case android.R.id.home:
                if (mCurrentMode == MODE_LIST) {
                    finish();
                } else {
                    removeFragment();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeFragment() {
        getFragmentManager().beginTransaction().remove(mFragment).commit();
    }

    @Override
    public void onFragmentIntraction(int mode, Bundle param) {
        switch (mode) {
            case MODE_ADD:
                mCurrentMode = mode;
                mFragment = new AddEditAccountFragment();
                break;
            case MODE_LIST:
                mFragment = new AccountListFragment();
                mCurrentMode = mode;
                break;
            case MODE_VIEW:
                mCurrentMode = mode;
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.account_fragment, mFragment).commit();
    }
}
