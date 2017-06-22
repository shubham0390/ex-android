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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import com.enfle.spendview.core.activities.ToolBarActivity;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.utils.Constants;
import com.enfle.spendview.utils.RxEventBus;
import com.enfle.spendview.R;

import org.parceler.Parcels;

public class ExpenseBookAddUpdateActivity extends ToolBarActivity {

    public static final String ACTION_ADD_MEMBER_COMPLETE = "Action:ExpenseBookAddUpdateActivity:ADD:Member";
    public static final String ARG_EXPENSEBOOK = "Arg:AddMemberFragment:Expensebook";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        toggleHomeBackButton(true);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getExtras();
        ExpenseBook expenseBook = null;
        if (bundle != null)
            expenseBook = Parcels.unwrap(bundle.getParcelable(ARG_EXPENSEBOOK));
        /*if action is not empty just install add memeber fragment*/
        if (!TextUtils.isEmpty(action) && Constants.ACTION_ADD_MEMBERS.equals(action)) {
            installMemberFragment(expenseBook.getName(), expenseBook.getOwnerId());
        } else {
            installExpenseBookFragment(expenseBook);
        }
        RxEventBus.getInstance().subscribe(String.class, string -> finish());
        RxEventBus.getInstance().subscribe(ExpenseBook.class, expenseBook1 -> {
            installMemberFragment(expenseBook1.getName(), expenseBook1.getOwnerId());
        });
    }

    private void installMemberFragment(String name, String ownerId) {
        setTitle(R.string.add_member);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, AddMembersFragment.getInstance(name, ownerId)).commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_create_new_expense_book;
    }

    private void installExpenseBookFragment(ExpenseBook expenseBook) {
        setTitle(R.string.add_expense_book);
        Fragment fragment = new AddUpdateExpenseBookFragment();
        if (expenseBook != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.EXTRA_EXPENSE_BOOK, Parcels.wrap(expenseBook));
        }
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
        setResult(RESULT_OK);
    }
}
