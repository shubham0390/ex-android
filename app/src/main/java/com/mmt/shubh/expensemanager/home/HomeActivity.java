package com.mmt.shubh.expensemanager.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.base.DrawerBaseActivity;
import com.mmt.shubh.expensemanager.expense.AddExpenseActivity;
import com.mmt.shubh.expensemanager.expensebook.add.ExpenseBookAddUpdateActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends DrawerBaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.tabs)
    TabLayout tabs;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.add_expense_fab)
    FloatingActionButton mAddExpenseFab;

    @Bind(R.id.add_expense_book_feb)
    FloatingActionButton mAddExpenseBookFab;

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private CharSequence Titles[] = {"DISTRIBUTION", "SUMMARY"};

    private int NumberOfTabs = 2;
    private HomeFragmentAdapter adapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initializeNavigationDrawer();
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), NumberOfTabs, Titles);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        pager.addOnPageChangeListener(this);
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
    protected void onResume() {
        super.onResume();
        // mNavigationView.setCheckedItem(R.id.home);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
