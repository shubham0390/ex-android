package com.mmt.shubh.expensemanager.expensebook.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.base.ToolBarActivity2;
import com.mmt.shubh.expensemanager.core.dagger.component.ConfigPersistentComponent;
import com.mmt.shubh.expensemanager.core.dagger.module.ActivityModule;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.expensebook.add.ExpenseBookAddUpdateActivity;
import com.mmt.shubh.expensemanager.expensebook.setting.ExpenseBookSettingFragment;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.utils.Constants;

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
