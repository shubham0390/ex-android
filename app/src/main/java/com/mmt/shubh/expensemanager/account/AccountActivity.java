package com.mmt.shubh.expensemanager.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Account;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AccountActivity extends ToolBarActivity implements IAccountActivityView<List<Account>> {

    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Inject
    AccountActivityPresenter mPresenter;

    AccountPagerAdapter mAccountPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        initializeToolbar();
        toggleHomeBackButton(true);
        setTitle(R.string.account);

        mPresenter.attachView(this);
        mPresenter.loadAllAccounts();
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerAccountActivityComponent.builder()
                .mainComponent(mainComponent)
                .moduleAccountActivity(new ModuleAccountActivity())
                .build()
                .inject(this);
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
        mPresenter.detachView(false);
    }
}
