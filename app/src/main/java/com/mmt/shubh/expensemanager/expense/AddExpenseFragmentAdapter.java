package com.mmt.shubh.expensemanager.expense;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shubham on 11/23/15.
 */
public class AddExpenseFragmentAdapter extends FragmentStatePagerAdapter {

    private static final int ITEM_COUNT = 2;

    public AddExpenseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddExpenseFragment();
            case 1:
                return new SharedFragment();
            default:
                throw new IllegalStateException("Invalid Id");
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Personal";
            case 1:
                return "Shared";
            default:
                throw new IllegalStateException("Invalid Id");
        }
    }
}
