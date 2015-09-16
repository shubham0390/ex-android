package com.mmt.shubh.expensemanager.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.IFragmentDataSharer;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.AddMembersToExpenseBookFragment;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.AddExpenseBookFragment;
import com.mmt.shubh.expensemanager.ui.fragment.base.IFragmentSwitcher;

public class AddExpenseBookActivity extends AppCompatActivity implements IFragmentSwitcher, IFragmentDataSharer {

    String mGroupName;
    String mGroupIconURI;
    String mGroupDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_expense_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);
        Fragment fragment = new AddExpenseBookFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (android.R.id.home == id) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void passData(Bundle sharedData) {
        mGroupName = sharedData.getString(Constants.EXTRA_GROUP_NAME);
        mGroupIconURI = sharedData.getString(Constants.EXTRA_GROUP_IMAGE_URI);
        mGroupDescription = sharedData.getString(Constants.EXTRA_GROUP_DESCRIPTION);
    }

    @Override
    public void replaceFragment(String tag, Bundle bundle) {

    }

    @Override
    public void replaceFragment(int fragmentId, Bundle bundle) {
        Fragment fragment = null;
        switch (fragmentId) {
            case Constants.ADDING_MEMBER_FRAGMENT:
                fragment = new AddMembersToExpenseBookFragment();
                Bundle groupInfo = new Bundle();
                groupInfo.putString(Constants.EXTRA_GROUP_NAME, mGroupName);
                groupInfo.putString(Constants.EXTRA_GROUP_IMAGE_URI, mGroupIconURI);
                groupInfo.putString(Constants.EXTRA_GROUP_DESCRIPTION, mGroupDescription);
                fragment.setArguments(groupInfo);
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public void removeFragment(String tag, Bundle bundle) {

    }

    @Override
    public void removeFragment(int id, Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(Activity.RESULT_OK);
    }
}
