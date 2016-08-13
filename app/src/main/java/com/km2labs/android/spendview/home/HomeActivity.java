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

package com.km2labs.android.spendview.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.base.DrawerBaseActivity;
import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponent;
import com.km2labs.android.spendview.expense.AddExpenseActivity;
import com.km2labs.android.spendview.expensebook.add.ExpenseBookAddUpdateActivity;

import butterknife.BindView;

public class HomeActivity extends DrawerBaseActivity<HomePresenter> {

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.add_expense_fab)
    FloatingActionButton mAddExpenseFab;

    @BindView(R.id.add_expense_book_feb)
    FloatingActionButton mAddExpenseBookFab;

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private Boolean isFabOpen = false;
    private View.OnClickListener mOnClickListener = v -> {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.add_expense_fab:
                Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
                startActivity(intent);
                break;
            case R.id.add_expense_book_feb:
                Intent intent1 = new Intent(HomeActivity.this, ExpenseBookAddUpdateActivity.class);
                startActivity(intent1);
                break;
        }
    };

    @Override
    protected void injectDependencies(ConfigPersistentComponent component) {
        /*component.activityComponent(new ActivityModule(this))
                .inject(this);*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeNavigationDrawer();
        setTitle(R.string.home_title);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        mFab.setOnClickListener(mOnClickListener);
        mAddExpenseFab.setOnClickListener(mOnClickListener);
        mAddExpenseBookFab.setOnClickListener(mOnClickListener);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }


    public void animateFAB() {

        if (isFabOpen) {
            mFab.startAnimation(rotate_backward);
            mAddExpenseFab.startAnimation(fab_close);
            mAddExpenseBookFab.startAnimation(fab_close);
            mAddExpenseFab.setClickable(false);
            mAddExpenseBookFab.setClickable(false);
            isFabOpen = false;

        } else {
            mFab.startAnimation(rotate_forward);
            mAddExpenseFab.startAnimation(fab_open);
            mAddExpenseBookFab.startAnimation(fab_open);
            mAddExpenseFab.setClickable(true);
            mAddExpenseBookFab.setClickable(true);
            isFabOpen = true;
        }
    }
}
