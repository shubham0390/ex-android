package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.TransactionContract;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 5:33 PM
 * TODO:Add class comment.
 */
public class TransactionSQLDataAdapter extends BaseSQLDataAdapter<Transaction> implements TransactionDataAdapter,
        TransactionContract {

    public TransactionSQLDataAdapter(Context context) {
        super(TRANSACTION_URI, context);
    }

    @Override
    public ContentValues toContentValues(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TRANSACTION_NAME, transaction.getName());
        values.put(TRANSACTION_AMOUNT, transaction.getAmount());
        values.put(TRANSACTION_DATE, transaction.getDate());
        values.put(TRANSACTION_TYPE, transaction.getType());
        return values;
    }

    @Override
    public void restore(Cursor cursor, Transaction transaction) {
        transaction.setName(cursor.getString(cursor.getColumnIndex(TRANSACTION_NAME)));
        transaction.setType(cursor.getString(cursor.getColumnIndex(TRANSACTION_TYPE)));
        transaction.setAmount(cursor.getInt(cursor.getColumnIndex(TRANSACTION_AMOUNT)));
        transaction.setDate(cursor.getLong(cursor.getColumnIndex(TRANSACTION_DATE)));
    }

    @Override
    public long create(Transaction transaction) {
        Uri uri = save(transaction);
        return 0;
    }

    @Override
    public int update(Transaction transaction) {
        return 0;
    }

    @Override
    public int delete(Transaction transaction) {
        return delete();
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
    public Transaction get(long id) {
        return restoreContentWithId(mContext, Transaction.class, TRANSACTION_URI, null, id);
    }

    @Override
    public List<Transaction> getAll() {
        String selection = ACCOUNT_KEY + " = (SELECT " + AccountContract._ID
                + " FROM" + AccountContract.TABLE_NAME +
                "WHERE " + AccountContract.ACCOUNT_TYPE + " = " + Account.TYPE_CASH + ")";
        Cursor cursor = mContext.getContentResolver().query(TRANSACTION_URI, null, selection, null, null);

        List<Transaction> transactionList = new ArrayList<>();
        if (cursor != null)
            try {
                while (cursor.moveToNext()) {
                    Transaction transaction = new Transaction();
                    restore(cursor, transaction);
                    transactionList.add(transaction);
                }
            } finally {
                cursor.close();
            }
        return transactionList;
    }
}
