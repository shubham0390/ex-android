package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.fragment.AccountListFragment;
import com.mmt.shubh.expensemanager.ui.listener.AccountFragmentIntractionListener;

public class AccountActivity extends ToolBarActivity implements AccountFragmentIntractionListener {

    public static final int MODE_ADD = 0;
    public static final int MODE_LIST = 1;
    public static final int MODE_VIEW = 2;

    private int mCurrentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initializeToolbar();
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        AccountListFragment listFragment = new AccountListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.account_fragment, listFragment).commit();

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
            case R.id.home:
                if (mCurrentMode == MODE_LIST) {
                    finish();
                } else {
                    onFragmentIntraction(MODE_LIST, null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentIntraction(int mode, Bundle param) {
        Fragment fragment = null;
        switch (mode) {
            case MODE_ADD:
                mCurrentMode = mode;
                break;
            case MODE_LIST:
                mCurrentMode = mode;
                break;
            case MODE_VIEW:
                mCurrentMode = mode;
                break;
        }
    }


}
