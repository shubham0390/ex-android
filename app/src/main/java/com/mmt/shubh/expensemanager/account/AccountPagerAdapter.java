package com.mmt.shubh.expensemanager.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.content.Account;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 12/Nov/2015,
 * 4:54 PM
 * TODO:Add class comment.
 */
public class AccountPagerAdapter extends FragmentStatePagerAdapter {

    List<Account> mAccounts = new ArrayList<>();

    public AccountPagerAdapter(FragmentManager fm) {
        super(fm);
        mAccounts = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mAccounts.size();
    }

    @Override
    public Fragment getItem(int position) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_ACCOUNT, Parcels.wrap(mAccounts.get(position)));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAccounts.get(position).getAccountName();
    }

    public void addData(List<Account> accounts) {
        mAccounts.addAll(accounts);
        notifyDataSetChanged();
    }
}
