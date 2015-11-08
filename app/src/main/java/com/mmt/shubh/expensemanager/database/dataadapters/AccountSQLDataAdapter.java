package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 2:51 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class AccountSQLDataAdapter extends BaseSQLDataAdapter<Account> implements AccountDataAdapter {


    @Inject
    public AccountSQLDataAdapter(Context context) {
        super(AccountContract.ACCOUNT_URI, context);
    }

    @Override
    public ContentValues toContentValues(Account account) {
        ContentValues values = new ContentValues();

        values.put(AccountContract.ACCOUNT_NAME, account.getAccountName());
        values.put(AccountContract.ACCOUNT_BALANCE, account.getAccountBalance());
        values.put(AccountContract.ACCOUNT_TYPE, account.getType());

        if (!TextUtils.isEmpty(account.getAccountNumber())) {
            values.put(AccountContract.ACCOUNT_NUMBER, account.getAccountNumber());
        }

        return values;
    }

    @Override
    public void restore(Cursor cursor, Account account) {
        account.setAccountName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME)));
        account.setAccountBalance(cursor.getLong(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE)));
        account.setType(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_TYPE)));
        account.setBankName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE)));
    }

    @Override
    public long create(Account account) {
        Uri uri = super.save(account);
        List paths = uri.getPathSegments();
        return Long.parseLong((String) paths.get(paths.size() - 1));
    }

    @Override
    public int update(Account account) {
        return 0;
    }

    @Override
    public int delete(Account account) {
        return delete(account.getId());
    }

    @Override
    public int delete(long id) {
        return delete(id);
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public Account get(long id) {
        return restoreContentWithId(Account.class, AccountContract.ACCOUNT_URI, null, id);
    }

    @Override
    public List<Account> getAll() {
        return restoreContent(Account.class, AccountContract.ACCOUNT_URI, null);
    }

    @Override
    public double getAccountBalance(long accountId) {
        return get(accountId).getAccountBalance();
    }

    @Override
    public void updateAmount(long accountId, double balanceAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountContract.ACCOUNT_BALANCE, balanceAmount);
        update(AccountContract.ACCOUNT_URI, accountId, contentValues);
    }
}
