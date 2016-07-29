package com.km2labs.android.spendview.expense.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.km2labs.android.spendview.expense.AddExpenseFragment;
import com.km2labs.android.spendview.expense.SharedFragment;


public class AddExpenseFragmentAdapter extends FragmentStatePagerAdapter {

    private static final int ITEM_COUNT = 1;

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
                throw new IllegalStateException("Invalid Position");
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
