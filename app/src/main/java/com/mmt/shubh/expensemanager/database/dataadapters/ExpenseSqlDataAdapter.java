package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseFilter;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.utils.DateUtil;
import com.mmt.shubh.expensemanager.utils.UnitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:10 PM
 * TODO:Add class comment.
 */
public class ExpenseSqlDataAdapter extends BaseSQLDataAdapter<Expense> implements ExpenseDataAdapter, ExpenseContract {

    @Inject
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
        values.put(EXPENSE_BOOK_KEY, expense.getExpenseBookId());
        values.put(CATEGORY_KEY, expense.getExpenseCategoryId());
        values.put(TRANSACTION_KEY, expense.getTransactionKey());
        values.put(OWNER_KEY, expense.getOwnerId());
        values.put(ACCOUNT_KEY, expense.getAccountKey());
        return values;
    }

    public void restore(Cursor cursor, Expense expense) {
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
    public long create(Expense expense) {
        Uri uri = save(expense);
        List paths = uri.getPathSegments();
        long id = Long.parseLong((String) paths.get(paths.size() - 1));
        expense.setId(id);
        return id;
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

    @Override
    public List<ExpenseListViewModel> getExpenseByMemberId(long memberId) {
        List<ExpenseListViewModel> list = new ArrayList<>();
        Uri uri = ContentUris.appendId(EXPENSE_LIST_URI.buildUpon(), memberId).build();
        Cursor cursor = mContext.getContentResolver().query(uri, null, ExpenseContract.EXPENSE_BOOK_KEY + " = ?",
                new String[]{String.valueOf(1)}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(parseCursorForExpenseViewModel(cursor));
            }
        }
        return list;
    }

    private ExpenseListViewModel parseCursorForExpenseViewModel(Cursor cursor) {
        ExpenseListViewModel model = new ExpenseListViewModel();
        model.setExpenseId(cursor.getLong(cursor.getColumnIndex(ExpenseContract.RECORD_ID)));
        model.setExpenseTitle(cursor.getString(cursor.getColumnIndex(ExpenseContract.EXPENSE_NAME)));
        model.setCategoryImage(cursor.getString(cursor.getColumnIndex(CategoryContract.CATEGORY_IMAGE_NAME)));
        model.setCategoryName(cursor.getString(cursor.getColumnIndex(CategoryContract.CATEGORY_NAME)));
        model.setExpenseAmount(UnitUtil.getLocalizedUnit(cursor.getDouble(cursor.getColumnIndex(ExpenseContract.EXPENSE_AMOUNT))));
        model.setExpenseDate(DateUtil.getLocalizedDate(cursor.getLong(cursor.getColumnIndex(ExpenseContract.EXPENSE_DATE))));
        model.setMemberName(cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_NAME)));
        model.setAccountName(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME)));
        model.setAccountType(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_TYPE)));

        return model;
    }

    @Override
    public List<ExpenseListViewModel> getExpenseByExpenseBookId(long expenseBookId) {
        return null;
    }

    @Override
    public List<ExpenseListViewModel> getExpenseByMemberId(ExpenseFilter filter) {
        List<ExpenseListViewModel> list = new ArrayList<>();
        Uri uri = ContentUris.appendId(EXPENSE_LIST_URI.buildUpon(), filter.getMemberId()).build();

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


        Cursor cursor = mContext.getContentResolver().query(uri, null, selection.toString(),
                null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(parseCursorForExpenseViewModel(cursor));
            }
        }
        return list;
    }
}
