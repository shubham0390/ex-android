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

import com.km2labs.android.spendview.database.content.Transaction;
import com.km2labs.android.spendview.database.content.contract.TransactionContract;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 5:33 PM
 * TODO:Add class comment.
 */
@Singleton
public class TransactionSQLDataAdapter extends BaseSQLDataAdapter<Transaction> implements TransactionDataAdapter,
        TransactionContract {

    @Inject
    public TransactionSQLDataAdapter(BriteDatabase briteDatabase) {
        super(TABLE_NAME, briteDatabase);
    }

    @Override
    public Transaction parseCursor(Cursor cursor) {
        Transaction transaction = new Transaction();
        transaction.setName(cursor.getString(cursor.getColumnIndex(TRANSACTION_NAME)));
        transaction.setType(cursor.getString(cursor.getColumnIndex(TRANSACTION_TYPE)));
        transaction.setAmount(cursor.getInt(cursor.getColumnIndex(TRANSACTION_AMOUNT)));
        transaction.setDate(cursor.getLong(cursor.getColumnIndex(TRANSACTION_DATE)));
        transaction.setAccountKey(cursor.getLong(cursor.getColumnIndex(ACCOUNT_KEY)));
        return transaction;
    }

    @Override
    public ContentValues toContentValues(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TRANSACTION_NAME, transaction.getName());
        values.put(TRANSACTION_AMOUNT, transaction.getAmount());
        values.put(TRANSACTION_DATE, transaction.getDate());
        values.put(TRANSACTION_TYPE, transaction.getType());
        values.put(ACCOUNT_KEY, transaction.getAccountKey());
        return values;
    }


    @Override
    protected void setId(Transaction transaction, long id) {
        transaction.setId(id);
    }
}
