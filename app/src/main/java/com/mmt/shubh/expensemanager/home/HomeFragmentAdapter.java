package com.mmt.shubh.expensemanager.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.member.MemberListFragment;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:06 PM
 * TODO:Add class comment.
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    CharSequence mTitles[];
    private int mTabCount;

    public HomeFragmentAdapter(FragmentManager fm, int tabCount, CharSequence[] pageTitels) {
        super(fm);
        mTabCount = tabCount;
        mTitles = pageTitels;
    }

    @Override
    public Fragment getItem(int position) {
        MemberListFragment fragment;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                fragment = new MemberListFragment();
                bundle.putInt(Constants.EXTRA_TYPE, MemberListFragment.TYPE_MEMBER);
                bundle.putBoolean(Constants.EXTRA_DELETE_MEMBER, false);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment = new MemberListFragment();
                bundle.putInt(Constants.EXTRA_TYPE, MemberListFragment.TYPE_MEMBER);
                bundle.putBoolean(Constants.EXTRA_DELETE_MEMBER, false);
                fragment.setArguments(bundle);
                return fragment;
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
