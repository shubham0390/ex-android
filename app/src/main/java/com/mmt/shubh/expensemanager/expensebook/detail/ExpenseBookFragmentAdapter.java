package com.mmt.shubh.expensemanager.expensebook.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:06 PM
 * TODO:Add class comment.
 */
public class ExpenseBookFragmentAdapter extends FragmentStatePagerAdapter {

    List<ExpenseBook> mExpenseBooks = new ArrayList<>();

    public ExpenseBookFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ExpenseBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_EXPENSE_BOOK_ID, mExpenseBooks.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mExpenseBooks.get(position).getName();
    }

    @Override
    public int getCount() {
        return mExpenseBooks.size();
    }

    public void addData(List<ExpenseBook> data) {
        mExpenseBooks.addAll(data);
        notifyDataSetChanged();
    }
}
