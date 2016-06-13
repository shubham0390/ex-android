package com.mmt.shubh.expensemanager.expensebook.add;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.IFragmentDataSharer;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.base.IFragmentSwitcher;
import com.mmt.shubh.expensemanager.expensebook.add.AddMembersToExpenseBookFragment;
import com.mmt.shubh.expensemanager.expensebook.add.AddUpdateExpenseBookFragment;

import butterknife.ButterKnife;

public class ExpenseBookAddUpdateActivity extends ToolBarActivity implements IFragmentSwitcher, IFragmentDataSharer {

    private Bundle mSharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_expense_book);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);

        String action = getIntent().getAction();
        mSharedData = getIntent().getExtras();
        /*if action is not empty just install add memeber fragment*/
        if (!TextUtils.isEmpty(action) && Constants.ACTION_ADD_MEMBERS.equals(action)) {
            replaceFragment(Constants.ADD_MEMBER_FRAGMENT, mSharedData);
        } else
            installExpenseBookFragment();
    }

    private void installExpenseBookFragment() {
        Fragment fragment = new AddUpdateExpenseBookFragment();

        fragment.setArguments(mSharedData);
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
        mSharedData = sharedData;
    }


    @Override
    public void replaceFragment(int fragmentId, Bundle bundle) {
        Fragment fragment = null;
        switch (fragmentId) {
            case Constants.ADD_MEMBER_FRAGMENT:
                fragment = new AddMembersToExpenseBookFragment();
                fragment.setArguments(bundle);
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
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
