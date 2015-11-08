package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 9:34 PM
 * TODO:Add class comment.
 */
public class MemberSQLExpenseDataAdapter extends BaseSQLDataAdapter<MemberExpense> implements MemberExpenseDataAdapter {

    @Inject
    public MemberSQLExpenseDataAdapter(Context context) {
        super(MemberExpenseContract.MEMBER_EXPENSE_URI, context);
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
    public void restore(Cursor cursor, MemberExpense memberExpense) {
        cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.EXPENSE_KEY));
        cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.EXPENSE_KEY));
        cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.SHARE_AMOUNT));
        cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.DEBIT_AMOUNT));
        cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.BALANCE_AMOUNT));
    }

    @Override
    public long create(MemberExpense memberExpense) {
        return create(memberExpense);
    }

    @Override
    public int update(MemberExpense memberExpense) {
        return 0;
    }

    @Override
    public int delete(MemberExpense memberExpense) {
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
    public MemberExpense get(long id) {
        return restoreContentWithId(MemberExpense.class, MemberExpenseContract.MEMBER_EXPENSE_URI, null, id);
    }

    @Override
    public List<MemberExpense> getAll() {
        return restoreContent(MemberExpense.class, MemberExpenseContract.MEMBER_EXPENSE_URI, null);
    }

    @Override
    public void create(List<MemberExpense> memberExpenses) {

    }
}
