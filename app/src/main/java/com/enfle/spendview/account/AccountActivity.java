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

package com.enfle.spendview.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.enfle.spendview.R;
import com.enfle.spendview.core.mvp.MVPActivity;
import com.enfle.spendview.core.mvp.lce.MVPLCEView;
import com.enfle.spendview.database.content.Account;


import java.util.List;

import butterknife.BindView;


public class AccountActivity extends MVPActivity<AccountActivityPresenter> implements MVPLCEView<List<Account>> {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    AccountPagerAdapter mAccountPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleHomeBackButton(true);
        setTitle(R.string.title_activity_account);
        mPresenter.subcribe(this);
        mPresenter.loadAllAccounts();
    }

    @Override
    protected int getLayout() {
        return R.layout.account_activity;
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
                finish();
        }
        return super.onOptionsItemSelected(item);
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
    public void setData(List<Account> data) {
        mAccountPagerAdapter = new AccountPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAccountPagerAdapter);
        mAccountPagerAdapter.addData(data);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadAllAccounts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubcribe(false);
    }
}
