package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Transaction;
import com.mmt.shubh.expensemanager.database.content.contract.TransactionContract;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 5:33 PM
 * TODO:Add class comment.
 */
public class TransactionRealmDataAdapter extends AbsRealmDataAdapter<Transaction> implements TransactionDataAdapter,
        TransactionContract {

    public TransactionRealmDataAdapter(Context context) {
        super(context);
    }


    @Override
    public long create(Transaction transaction) {
        return 0;
    }

    @Override
    public Transaction delete(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction delete(long id) {
        return null;
    }


    @Override
    public Transaction get(long id) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }



}
