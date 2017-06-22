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

package com.enfle.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.enfle.spendview.core.Select;
import com.enfle.spendview.database.api.ExpenseDataAdapter;
import com.enfle.spendview.database.content.Expense;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.database.content.ExpenseCategory;
import com.enfle.spendview.database.content.MemberExpense;
import com.enfle.spendview.database.content.ModelFactory;
import com.enfle.spendview.database.content.contract.AccountContract;
import com.enfle.spendview.database.content.contract.CategoryContract;
import com.enfle.spendview.database.content.contract.ExpenseBookContract;
import com.enfle.spendview.database.content.contract.ExpenseContract;
import com.enfle.spendview.database.content.contract.MemberContract;
import com.enfle.spendview.database.content.contract.MemberExpenseContract;
import com.enfle.spendview.expense.ExpenseFilter;
import com.enfle.spendview.expense.ExpenseListViewModel;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 5:10 PM
 * TODO:Add class comment.
 */
@Singleton
public class ExpenseSqlDataAdapter extends BaseSQLDataAdapter<Expense> implements ExpenseDataAdapter, ExpenseContract {

    @Inject
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
    protected void setId(Expense expense, long id) {
        expense.setId(id);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpensesWithFilters(long memberId) {

        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " eb._id ," +
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
        q = q + " WHERE " + ExpenseContract.OWNER_KEY + " = " + memberId + " ORDER BY e.expense_date DESC";

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    private ExpenseListViewModel parseCursorForExpenseViewModel(Cursor cursor) {

        ExpenseListViewModel model = new ExpenseListViewModel();
        model.setExpenseId(cursor.getLong(0));
        model.setExpenseAmount(cursor.getDouble(1));
        model.setExpenseDate(cursor.getLong(2));
        model.setTransactionId(cursor.getLong(3));
        model.setExpenseTitle(cursor.getString(4));
        //model.setCategoryImage(cursor.getInt(cursor.getColumnIndex(CategoryContract.CATEGORY_IMAGE_NAME)));
        model.setCategoryName(cursor.getString(5));
        model.setExpenseBookId(6);
        model.setExpenseBookName(cursor.getString(7));
        model.setMemberName(cursor.getString(8));
        model.setOwnerId(cursor.getLong(9));
        model.setAccountName(cursor.getString(10));
        model.setAccountType(cursor.getString(11));
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
                " eb._id ," +
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
        q = q + " WHERE " + ExpenseContract.EXPENSE_BOOK_KEY + " = " + expenseBookId + " ORDER BY e.expense_date DESC";

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpensesWithFilters(ExpenseFilter filter) {
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
                selection.append(" AND ");
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
                selection.append(" AND ");
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
                " eb._id ," +
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
            q += " WHERE " + selection.toString();

        q += " ORDER BY e.expense_date DESC ";
        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenseByAccountId(final long accountId) {
        long currentYearTimeInMilli = 0L;
        String q = "SELECT "
                + " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " eb._id ," +
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
        q = q + " WHERE " + ExpenseContract.ACCOUNT_KEY + " = " + accountId + " AND e.expense_date >" + currentYearTimeInMilli + " ORDER BY e.expense_date DESC";

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getExpenses(String selection) {

        String q = "SELECT "
                + " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " eb._id ," +
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
        q = q + " WHERE " + selection + " ORDER BY e.expense_date DESC";

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<ExpenseListViewModel>> getAllSharedExpenseList(long id, long id2) {
        String q = "SELECT " +
                " e._id," +
                " e.expense_amount," +
                " e.expense_date," +
                " e.transaction_key ," +
                " e.expense_name ," +
                " ec.category_name," +
                " eb._id ," +
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
        q = q + " WHERE  e._id IN "
                + " ( "
                + " SELECT expense_key "
                + " FROM member_expense "
                + " WHERE expense_key "
                + " IN "
                + " ("
                + " SELECT expense_key "
                + " FROM member_expense "
                + " WHERE member_key = " + id
                + " )"
                + " AND member_key = " + id2 + ") GROUP BY eb._id ORDER BY e.expense_date ";

        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursorForExpenseViewModel);
    }

    @Override
    public Observable<List<MemberExpense>> getSharedExpenseDetails(long id, long id2) {

        String s = " SELECT * FROM "
                + MemberExpenseContract.TABLE_NAME
                + " WHERE "
                + MemberExpenseContract.MEMBER_KEY
                + " IN "
                + " ( SELECT "
                + MemberExpenseContract.EXPENSE_KEY
                + " FROM " + MemberExpenseContract.TABLE_NAME
                + " WHERE " + MemberExpenseContract.MEMBER_KEY + " = " + id
                + " ) AND " + MemberExpenseContract.MEMBER_KEY + " = " + id2;
        return mBriteDatabase.createQuery(MemberExpenseContract.TABLE_NAME, s).mapToList(this::parseMemberExpense);
    }

    @Override
    public boolean isAnyExpenseExists(long memberId, long expenseBookId) {
        String query = new Select()
                .addColumns(new String[]{ExpenseContract._ID})
                .from(mTableName)
                .where(ExpenseContract._ID).in(new Select()
                        .addColumns(new String[]{MemberExpenseContract.EXPENSE_KEY})
                        .where(MemberExpenseContract.MEMBER_KEY).equql(memberId))
                .and()
                .where(ExpenseContract.EXPENSE_BOOK_KEY).equql(expenseBookId).toString();
        Cursor cursor = null;
        try {

            cursor = mBriteDatabase.query(query);
            if (cursor != null) {
                return cursor.getCount() > 0;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return false;
    }

    private MemberExpense parseMemberExpense(Cursor cursor) {
        MemberExpense memberExpense = ModelFactory.getNewMemberExpense();
        memberExpense.setMemberKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.MEMBER_KEY)));
        memberExpense.setExpenseKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.EXPENSE_KEY)));
        memberExpense.setBalanceAmount(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.BALANCE_AMOUNT)));
        memberExpense.setDebitAmount(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.DEBIT_AMOUNT)));
        memberExpense.setShareAmount(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.SHARE_AMOUNT)));
        return memberExpense;
    }
}
