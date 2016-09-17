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

package com.km2labs.android.spendview.expensebook.add;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import com.km2labs.android.spendview.core.base.ToolBarActivityV3;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.expenseview.android.R;

import butterknife.ButterKnife;

public class ExpenseBookAddUpdateActivity extends ToolBarActivityV3 {

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

    @Override
    protected <T> T createComponent(MainComponent mainComponent) {
        return null;
    }

    @Override
    protected void injectDependencies(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return 0;
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
