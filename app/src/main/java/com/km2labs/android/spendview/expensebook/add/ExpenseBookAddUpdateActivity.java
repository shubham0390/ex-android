package com.km2labs.android.spendview.expensebook.add;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import com.km2labs.android.spendview.core.base.ToolBarActivity;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.shubh.expensemanager.R;

import butterknife.ButterKnife;

public class ExpenseBookAddUpdateActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_expense_book);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);

        String action = getIntent().getAction();
        /*if action is not empty just install add memeber fragment*/
        if (!TextUtils.isEmpty(action) && Constants.ACTION_ADD_MEMBERS.equals(action)) {
        } else
            installExpenseBookFragment();
    }

    private void installExpenseBookFragment() {
        Fragment fragment = new AddUpdateExpenseBookFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
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
    protected void onDestroy() {
        super.onDestroy();
        setResult(Activity.RESULT_OK);
    }
}
