package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;

import java.util.List;

import api.ExpenseBookDataAdapter;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 4:04 PM
 * TODO:Add class comment.
 */
public class ExpenseBookSQLDataAdapter extends BaseSQLDataAdapter <ExpenseBook> implements ExpenseBookDataAdapter<ExpenseBook>, ExpenseBookContract {



    public ExpenseBookSQLDataAdapter(Context context) {
        super(ExpenseBookContract.EXPENSE_BOOK_URI,context);
    }

    /**
     * Check if any group present for provided group name.
     *
     * @param context
     * @param groupName
     * @return <code>true</code> if {@link  ExpenseBook} already present otherwise
     * <code>false</code>.
     */
    public boolean isExpenseBookExsist(Context context, String groupName) {
        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(EXPENSE_BOOK_URI,
                    BaseSQLDataAdapter.ID_PROJECTION, ExpenseBookContract.EXPENSE_BOOK_NAME + " = ?",
                    new String[]{groupName}, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return false;
            } else {
                return true;
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Cursor getAllExpenseBook(Context context) {
        return context.getContentResolver().query(EXPENSE_BOOK_URI, null, null, null,
                ExpenseBookContract.EXPENSE_BOOK_NAME);
    }

    public ContentValues toContentValues(ExpenseBook expenseBook) {
        ContentValues values = new ContentValues();
        values.put(EXPENSE_BOOK_NAME, expenseBook.getName());
        values.put(EXPENSE_BOOK_PROFILE_IMAGE_URI, expenseBook.getProfileImagePath());
        values.put(EXPENSE_BOOK_DESCRIPTION, expenseBook.getDescription());
        values.put(EXPENSE_BOOK_TYPE, expenseBook.getType());
        return values;
    }

    public void restore(Cursor cursor, ExpenseBook expenseBook) {
        expenseBook.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        expenseBook.setName(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_NAME)));
        expenseBook.setDescription(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_DESCRIPTION)));
        expenseBook.setType(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_TYPE)));
        expenseBook.setProfileImagePath(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_PROFILE_IMAGE_URI)));
    }

    private UserInfo getUser(long aLong) {
        return null;
    }

    public void addMembers(List members) {
        MemberSQLDataAdapter sqlDataAdapter = new MemberSQLDataAdapter(mContext);
        sqlDataAdapter.create(members);
    }

    @Override
    public long create(ExpenseBook expenseBook) {
        Uri uri = save(expenseBook);
        List paths = uri.getPathSegments();
        return Long.parseLong((String) paths.get(paths.size() - 1));
    }

    @Override
    public int update(ExpenseBook expenseBook) {
        return 0;
    }

    @Override
    public int delete(ExpenseBook expenseBook) {
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
    public ExpenseBook get(long id) {
        return null;
    }

    @Override
    public List<ExpenseBook> getAll() {
      /*  List<ExpenseBook> expenseBooks = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(AccountContract.ACCOUNT_URI,
                AccountContract.USER_PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    ExpenseBook expenseBook = new ExpenseBook();
                    restore(cursor, expenseBook);
                    expenseBooks.add(expenseBook);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }*/
        return null;
    }

    @Override
    public void addMember(ExpenseBook expenseBook) {

    }
}
