package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 2:51 PM
 * TODO:Add class comment.
 */
public class AccountRealmDataAdapter extends AbsRealmDataAdapter<Account> implements AccountDataAdapter {


    public AccountRealmDataAdapter(Context context) {
        super(context);
    }

    @Override
    public long create(Account account) {
        account = super.save(account);
        return account.getId();
    }

    @Override
    public Account delete(Account account) {
        return account;
    }

    @Override
    public Account delete(long id) {
        return null;
    }

    @Override
    public void deleteAll(Class<Account> tClass) {
    }

    @Override
    public Account get(long id) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return null;
    }
}
