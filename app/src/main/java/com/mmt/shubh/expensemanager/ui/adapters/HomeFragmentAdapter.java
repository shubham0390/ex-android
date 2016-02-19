package com.mmt.shubh.expensemanager.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.ui.fragment.MemberListFragment;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:06 PM
 * TODO:Add class comment.
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    CharSequence mTitles[];

    public HomeFragmentAdapter(FragmentManager fm, int tabCount, CharSequence[] pageTitels) {
        super(fm);
        mTabCount = tabCount;
        mTitles = pageTitels;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MemberListFragment();
            case 1:
                return new MemberListFragment();
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
