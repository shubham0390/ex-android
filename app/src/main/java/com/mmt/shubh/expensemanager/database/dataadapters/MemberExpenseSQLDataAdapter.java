package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseContract;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 9:34 PM
 * TODO:Add class comment.
 */
public class MemberExpenseSQLDataAdapter extends AbstractSQLDataAdapter<MemberExpense> implements MemberExpenseDataAdapter {

    private String LOG_TAG = getClass().getName();

    @Inject
    public MemberExpenseSQLDataAdapter(BriteDatabase briteDatabase) {
        super(MemberExpenseContract.TABLE_NAME, briteDatabase);
    }

    @Override
    public MemberExpense parseCursor(Cursor cursor) {
        MemberExpense memberExpense = new MemberExpense();
        memberExpense.setExpenseKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.EXPENSE_KEY)));
        memberExpense.setMemberKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.MEMBER_KEY)));
        memberExpense.setShareAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.SHARE_AMOUNT)));
        memberExpense.setDebitAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.DEBIT_AMOUNT)));
        memberExpense.setBalanceAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.BALANCE_AMOUNT)));
        return null;
    }

    @Override
    public ContentValues toContentValues(MemberExpense memberExpense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberExpenseContract.EXPENSE_KEY, memberExpense.getExpenseKey());
        contentValues.put(MemberExpenseContract.MEMBER_KEY, memberExpense.getMemberKey());
        contentValues.put(MemberExpenseContract.SHARE_AMOUNT, memberExpense.getShareAmount());
        contentValues.put(MemberExpenseContract.DEBIT_AMOUNT, memberExpense.getDebitAmount());
        contentValues.put(MemberExpenseContract.BALANCE_AMOUNT, memberExpense.getBalanceAmount());
        return contentValues;
    }


    @Override
    protected void setTaskId(MemberExpense memberExpense, long id) {
        //     memberExpense.set
    }
}
