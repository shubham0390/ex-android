/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enfle.spendview.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.enfle.spendview.database.content.Account;
import com.enfle.spendview.utils.Constants;

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
