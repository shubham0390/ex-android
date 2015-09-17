package com.mmt.shubh.expensemanager.ui.adapters.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.ui.fragment.MemberListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.ExpenseDistributionFragment;
import com.mmt.shubh.expensemanager.ui.fragment.graph.ExpenseBookGraphFragment;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:06 PM
 * TODO:Add class comment.
 */
public class ExpenseBookDetailFragmentAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    CharSequence mTitles[];

    public ExpenseBookDetailFragmentAdapter(FragmentManager fm, int tabCount, CharSequence[] pageTitels) {
        super(fm);
        mTabCount = tabCount;
        mTitles = pageTitels;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExpenseBookGraphFragment();
            case 1:
                return new ExpenseDistributionFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
