package com.mmt.shubh.expensemanager.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.ui.fragment.account.AccountViewFragment;

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

    public AccountPagerAdapter(FragmentManager fm,List<Account> accounts) {
        super(fm);
        mAccounts = accounts;
    }

    @Override
    public int getCount() {
        return mAccounts.size();
    }

    @Override
    public Fragment getItem(int position) {
        AccountViewFragment fragment = new AccountViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_ACCOUNT, Parcels.wrap(mAccounts.get(position)));
        fragment.setArguments(bundle);
        return new AccountViewFragment();
    }

}
