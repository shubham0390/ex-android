package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;

import java.util.List;

import api.exceptions.AccountDataAdapter;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 2:51 PM
 * TODO:Add class comment.
 */
public class AccountSQLDataAdapter extends BaseSQLDataAdapter<Account> implements AccountDataAdapter {


    public AccountSQLDataAdapter(Context context) {
        super(context);
    }

    @Override
    public ContentValues toContentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(AccountContract.ACCOUNT_NAME, account.getAccountName());
        values.put(AccountContract.ACCOUNT_BALANCE, account.getAccountBalance());
        return values;
    }

    @Override
    public void restore(Cursor cursor, Account account) {
        cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME));
        cursor.getLong(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE));
    }

    @Override
    public long create(Account account) {
        super.save(account);
        return 0;
    }

    @Override
    public int update(Account account) {
        return 0;
    }

    @Override
    public int delete(Account account) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
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
