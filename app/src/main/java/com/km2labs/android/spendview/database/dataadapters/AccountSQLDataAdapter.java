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

package com.km2labs.android.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.database.content.contract.AccountContract;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 2:51 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class AccountSQLDataAdapter extends BaseSQLDataAdapter<Account> implements AccountDataAdapter, AccountContract {


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
    protected void setId(Account account, long id) {
        account.setId(id);
    }

    @Override
    public double getAccountBalance(long accountId) {
        final double[] accountBalance = {0D};
        getSingleResultByColumn(AccountContract._ID, accountId)
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(d -> {
                            accountBalance[0] = d.getAccountBalance();
                        }, e -> {
                            Timber.e(e.getMessage());
                        }
                );
        return accountBalance[0];
    }

    @Override
    @WorkerThread
    public void updateAmount(long accountId, double balanceAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountContract.ACCOUNT_BALANCE, balanceAmount);
        mBriteDatabase.update(mTableName, contentValues, _ID + " = " + accountId);

    }

    @Override
    public Observable<List<Account>> loadAllAccounts() {
        return getAll();
    }

    @Override
    public Observable<List<Account>> getAccountByMember(long memberId) {
        return getAll();
    }
}
