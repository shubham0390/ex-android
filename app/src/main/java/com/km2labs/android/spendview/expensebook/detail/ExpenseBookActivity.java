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

package com.km2labs.android.spendview.expensebook.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.km2labs.android.spendview.core.base.ToolBarActivity2;
import com.km2labs.android.spendview.core.dagger.module.ActivityModule;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponent;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.expensebook.add.ExpenseBookAddUpdateActivity;
import com.km2labs.android.spendview.expensebook.setting.ExpenseBookSettingFragment;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import org.parceler.Parcels;

import java.util.List;


import butterknife.BindView;
import icepick.State;

public class ExpenseBookActivity extends ToolBarActivity2<ExpenseBookActivityPresenter> implements MVPLCEView<List<ExpenseBook>> {

    private static final String TAG_SETTING_FRAGMENT = "settingFragment";

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    ExpenseBookFragmentAdapter adapter;

    ExpenseBook mCurrentExpenseBook;

    @State
    boolean isSettingFragmentInstalled;

    @State
    int mCurrentPosition;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
        setToolbar();
        setTabs();
        if (isSettingFragmentInstalled) {
            installSettingFragment();
        }
    }

    @Override
    protected void injectDependencies(ConfigPersistentComponent component) {
        component.activityComponent(new ActivityModule(this)).inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_expense_book_detail;
    }

    private void setTabs() {
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentExpenseBook = adapter.getDataAtPosition(position);
            mCurrentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        loadData(false);
    }


    private void setToolbar() {
        setTitle(R.string.expense_book);
        toggleHomeBackButton(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add_expensebook:
                Intent intent = new Intent(this, ExpenseBookAddUpdateActivity.class);
                startActivity(intent);
                return true;
            default:
                installSettingFragment();
                return false;
        }
    }

    private void installSettingFragment() {
        Fragment settingFragment = new ExpenseBookSettingFragment();
        Bundle expenseBookInfo = new Bundle();
        expenseBookInfo.putParcelable(Constants.EXTRA_EXPENSE_BOOK, Parcels.wrap(adapter.getDataAtPosition(mCurrentPosition)));
        settingFragment.setArguments(expenseBookInfo);
        isSettingFragmentInstalled = true;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, settingFragment, TAG_SETTING_FRAGMENT)
                .commit();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(List<ExpenseBook> data) {
        adapter = new ExpenseBookFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        adapter.addData(data);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(mCurrentPosition, true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadExpenseBookList();
    }


    @Override
    public void onBackPressed() {
        if (isSettingFragmentInstalled) {
            isSettingFragmentInstalled = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(TAG_SETTING_FRAGMENT);
            if (fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();
        } else {
            super.onBackPressed();
        }
    }
}
