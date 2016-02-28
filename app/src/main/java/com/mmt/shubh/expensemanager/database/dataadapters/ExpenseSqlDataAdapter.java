package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.expense.ExpenseFilter;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:10 PM
 * TODO:Add class comment.
 */
public class ExpenseSqlDataAdapter extends AbstractSQLDataAdapter<Expense> implements ExpenseDataAdapter, ExpenseContract {

    public ExpenseSqlDataAdapter(BriteDatabase briteDatabase) {
        super(ExpenseContract.TABLE_NAME, briteDatabase);
    }

    @Override
    public Expense parseCursor(Cursor cursor) {
        Expense expense = new Expense();
        expense.setId(cursor.getLong(COLUMN_EXPENSE_ID));
        expense.setExpenseAmount(cursor.getDouble(COLUMN_EXPENSE_AMOUNT));
        expense.setExpenseDate(cursor.getLong(COLUMN_EXPENSE_DATE));
        expense.setExpenseName(cursor.getString(COLUMN_EXPENSE_NAME));
        expense.setExpensePlace(cursor.getString(COLUMN_EXPENSE_PLACE));
        expense.setExpenseDescription(cursor.getString(COLUMN_EXPENSE_DESCRIPTION));
        expense.setMemberMap(getMemberMap(expense.getId()));
        expense.setExpenseBookId(cursor.getLong(COLUMN_EXPENSE_BOOK));
        expense.setExpenseCategoryId(cursor.getLong(COLUMN_CATEGORY_KEY));
        expense.setOwnerId(cursor.getLong(cursor.getColumnIndex(OWNER_KEY)));
        return expense;
    }

    @Override
    public ContentValues toContentValues(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(EXPENSE_NAME, expense.getExpenseName());
        values.put(EXPENSE_AMOUNT, expense.getExpenseAmount());
        values.put(EXPENSE_DATE, expense.getExpenseDate());
        values.put(EXPENSE_PLACE, expense.getExpensePlace());
        values.put(EXPENSE_DESCRIPTION, expense.getExpenseDescription());
        values.put(EXPENSE_BOOK_KEY, expense.getExpenseBookId());
        values.put(CATEGORY_KEY, expense.getExpenseCategoryId());
        values.put(TRANSACTION_KEY, expense.getTransactionKey());
        values.put(OWNER_KEY, expense.getOwnerId());
        values.put(ACCOUNT_KEY, expense.getAccountKey());
        return values;
    }

    public void restore(Cursor cursor, Expense expense) {

    }

    private ExpenseCategory getCategory(long aLong) {
        return null;
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    private Map<Long, Double> getMemberMap(long aLong) {
        return null;
    }


    @Override
    protected void setTaskId(Expense expense, long id) {
        expense.setId(id);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenseByMemberId(long memberId) {

        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " ec.category_image ," +
                " eb.name ," +
                " m.name ," +
                " m._id ," +
                " a.account_name ," +
                " a.account_type " +
                " FROM "
                + ExpenseContract.TABLE_NAME
                + " e INNER JOIN " + CategoryContract.TABLE_NAME + " ec ON ec._id=e.category_key  " +
                " INNER JOIN " + ExpenseBookContract.TABLE_NAME + " eb ON eb._id = e.expense_book_key" +
                " INNER JOIN " + AccountContract.TABLE_NAME + " a ON a._id = e.account_key" +
                " INNER JOIN " + MemberContract.TABLE_NAME + " m ON  m._id = " + memberId;
        q = q + " WHERE " + ExpenseContract.OWNER_KEY + " = " + memberId;

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    private ExpenseListViewModel parseCursorForExpenseViewModel(Cursor cursor) {

        ExpenseListViewModel model = new ExpenseListViewModel();
        model.setExpenseId(cursor.getLong(cursor.getColumnIndex(ExpenseContract.RECORD_ID)));
        model.setExpenseTitle(cursor.getString(cursor.getColumnIndex(ExpenseContract.EXPENSE_NAME)));
        model.setCategoryImage(cursor.getInt(cursor.getColumnIndex(CategoryContract.CATEGORY_IMAGE_NAME)));
        model.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryContract.CATEGORY_NAME)));
        model.setExpenseAmount(cursor.getDouble(cursor.getColumnIndex(ExpenseContract.EXPENSE_AMOUNT)));
        model.setExpenseDate(cursor.getLong(cursor.getColumnIndex(ExpenseContract.EXPENSE_DATE)));
        model.setMemberName(cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_NAME)));
        model.setAccountName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME)));
        model.setAccountType(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_TYPE)));
        model.setExpenseBookName(cursor.getString(cursor.getColumnIndex(ExpenseBookContract.EXPENSE_BOOK_NAME)));
        return model;
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenseByExpenseBookId(long expenseBookId) {
        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " ec.category_image ," +
                " eb.name ," +
                " m.name ," +
                " m._id ," +
                " a.account_name ," +
                " a.account_type " +
                " FROM "
                + ExpenseContract.TABLE_NAME
                + " e INNER JOIN " + CategoryContract.TABLE_NAME + " ec ON e.category_key = ec._id  "
                + " INNER JOIN " + ExpenseBookContract.TABLE_NAME + " eb ON e.expense_book_key = eb._id"
                + " INNER JOIN " + AccountContract.TABLE_NAME + " a ON e.account_key = a._id"
                + " INNER JOIN " + MemberContract.TABLE_NAME + " m ON  e.owner_key = m._id";
        q = q + " WHERE " + ExpenseContract.EXPENSE_BOOK_KEY + " = " + expenseBookId;

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenseByMemberId(ExpenseFilter filter) {
        StringBuilder selection = new StringBuilder();
        boolean isExits = false;
        if (filter.getExpenseBookId() > 0) {
            selection.append(ExpenseContract.EXPENSE_BOOK_KEY);
            selection.append(" = ");
            selection.append(String.valueOf(filter.getExpenseBookId()));
            isExits = true;
        }

        if (filter.getTimeFilter() > 1) {
            if (isExits) {
                selection.append("AND");
            }
            selection.append(EXPENSE_DATE);
            selection.append(" BETWEEN ");
            selection.append(String.valueOf(filter.getEndDate()));
            selection.append(" AND ");
            selection.append(String.valueOf(filter.getStartDate()));
            isExits = true;
        }

        if (filter.getCategoryId() > 0) {
            if (isExits) {
                selection.append("AND");
            }
            selection.append(CATEGORY_KEY);
            selection.append(" = ");
            selection.append(String.valueOf(filter.getCategoryId()));
            isExits = true;
        }
        if (filter.getAccountId() > 0) {
            if (isExits) {
                selection.append(" AND ");
            }
            selection.append(ACCOUNT_KEY);
            selection.append(" = ");
            selection.append(filter.getAccountId());
        }

        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " ec.category_image ," +
                " eb.name ," +
                " m.name ," +
                " m._id ," +
                " a.account_name ," +
                " a.account_type " +
                " FROM "
                + ExpenseContract.TABLE_NAME
                + " e INNER JOIN " + CategoryContract.TABLE_NAME + " ec ON e.category_key = ec._id  "
                + " INNER JOIN " + ExpenseBookContract.TABLE_NAME + " eb ON e.expense_book_key = eb._id"
                + " INNER JOIN " + AccountContract.TABLE_NAME + " a ON e.account_key = a._id"
                + " INNER JOIN " + MemberContract.TABLE_NAME + " m ON  e.owner_key = m._id";
        String selectionString = selection.toString();
        if (!TextUtils.isEmpty(selectionString))
            q = q + " WHERE " + selection.toString();
        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenseByAccountId(final long accountId) {
        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " ec.category_image ," +
                " eb.name ," +
                " m.name ," +
                " m._id ," +
                " a.account_name ," +
                " a.account_type " +
                " FROM "
                + ExpenseContract.TABLE_NAME
                + " e INNER JOIN " + CategoryContract.TABLE_NAME + " ec ON ec._id=e.category_key  "
                + " INNER JOIN " + ExpenseBookContract.TABLE_NAME + " eb ON eb._id = e.expense_book_key"
                + " INNER JOIN " + AccountContract.TABLE_NAME + " a ON a._id = e.account_key"
                + " INNER JOIN " + MemberContract.TABLE_NAME + " m ON  m._id = e.owner_key";
        q = q + " WHERE " + ExpenseContract.ACCOUNT_KEY + " = " + accountId;

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

}
