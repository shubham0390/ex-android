package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.database.content.contract.TransactionContract;
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
    protected void setTaskId(Transaction transaction, long id) {
        transaction.setId(id);
    }
}
