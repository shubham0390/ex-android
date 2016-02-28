package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 2:51 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class AccountSQLDataAdapter extends AbstractSQLDataAdapter<Account> implements AccountDataAdapter {


    @Inject
    public AccountSQLDataAdapter(BriteDatabase briteDatabase) {
        super(AccountContract.TABLE_NAME, briteDatabase);
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
    public Account parseCursor(Cursor cursor) {
        Account account = new Account();
        account.setId(cursor.getLong(cursor.getColumnIndex(AccountContract._ID)));
        account.setAccountName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME)));
        account.setAccountBalance(cursor.getLong(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE)));
        account.setType(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_TYPE)));
        account.setBankName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE)));
        return account;
    }

    @Override
    protected void setTaskId(Account account, long id) {
        account.setId(id);
    }

    @Override
    public double getAccountBalance(long accountId) {
        return 3000;//get(accountId).getAccountBalance();
    }

    @Override
    public void updateAmount(long accountId, double balanceAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountContract.ACCOUNT_BALANCE, balanceAmount);
        //update(AccountContract.ACCOUNT_URI, accountId, contentValues);
    }

    @Override
    public Observable<List<Account>> loadAllAccounts() {
        return getAll();
    }
}
