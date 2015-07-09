package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import api.ExpenseDataAdapter;

import com.mmt.shubh.expensemanager.database.content.Category;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;

import java.util.Calendar;
import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class ExpenseSqlDataAdapter extends BaseSQLDataAdapter<Expense> implements ExpenseDataAdapter<Expense>, ExpenseContract {


    public ExpenseSqlDataAdapter(Context context) {
        super(ExpenseContract.EXPENSE_URI, context);
    }

    public ContentValues toContentValues(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(EXPENSE_NAME, expense.getExpenseName());
        values.put(EXPENSE_AMOUNT, expense.getExpenseAmount());
        values.put(EXPENSE_DATE, expense.getExpenseDate());
        values.put(EXPENSE_PLACE, expense.getExpensePlace());
        values.put(EXPENSE_DESCRIPTION, expense.getExpenseDescription());
        values.put(EXPENSE_BOOK_KEY, expense.getExpenseBook().getId());
        values.put(CATEGORY_KEY, expense.getCategory().getId());
        return values;
    }

    public void restore(Cursor cursor, Expense expense) {
        expense.setId(cursor.getLong(COLUMN_EXPENSE_ID));
        expense.setExpenseAmount(cursor.getString(COLUMN_EXPENSE_AMOUNT));
        expense.setExpenseDate(cursor.getLong(COLUMN_EXPENSE_DATE));
        expense.setExpenseName(cursor.getString(COLUMN_EXPENSE_NAME));
        expense.setExpensePlace(cursor.getString(COLUMN_EXPENSE_PLACE));
        expense.setExpenseDescription(cursor.getString(COLUMN_EXPENSE_DESCRIPTION));
        expense.setMemberList(getMemberList(expense.getId()));
        expense.setExpenseBook(getExpenseBook(cursor.getLong(COLUMN_EXPENSE_BOOK)));
        expense.setCategory(getCategory(cursor.getLong(COLUMN_CATEGORY_KEY)));
    }

    private Category getCategory(long aLong) {
        return null;
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    private List<Member> getMemberList(long aLong) {
        return null;
    }

    @Override
    public long create(Expense expense) {
        save(expense);
        return 0;
    }

    @Override
    public int update(Expense expense) {
        return 0;
    }

    @Override
    public int delete(Expense expense) {
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
    public Expense get(long id) {
        return null;
    }

    @Override
    public List<Expense> getAll() {
        return null;
    }

    /*public List<Exp> getAllExpenseForMemberAndExpenseBookGroupByDate(Context context, long expenseBookId, long memberId) {
        Calendar calendar = Calendar.getInstance();
        return context.getContentResolver().query(MemberContract.MEMBER_EXPENSE_BOOK_URI,
                MemberContract.PROJECTION_MEMBER_EXPENSE_BOOK,
                MemberContract.SELECTION_EXPENSE_MEMBER_KEY_AND_DATE,
                new String[]{String.valueOf(memberId), String.valueOf(calendar.get(Calendar.YEAR))}, null);
    }*/
}
