package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.BaseContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;

import java.util.ArrayList;
import java.util.List;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseBookContract;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 4:04 PM
 * TODO:Add class comment.
 */
public class ExpenseBookSQLDataAdapter extends BaseSQLDataAdapter<ExpenseBook> implements ExpenseBookDataAdapter, ExpenseBookContract {


    public ExpenseBookSQLDataAdapter(Context context) {
        super(ExpenseBookContract.EXPENSE_BOOK_URI, context);
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
                    BaseContract.ID_PROJECTION, ExpenseBookContract.EXPENSE_BOOK_NAME + " = ?",
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
        values.put(EXPENSE_BOOK_CREATION_TIME, expenseBook.getCreationTime());
        values.put(OWNER_KEY, expenseBook.getOwner().getId());
        return values;
    }

    public void restore(Cursor cursor, ExpenseBook expenseBook) {
        expenseBook.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        expenseBook.setName(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_NAME)));
        expenseBook.setDescription(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_DESCRIPTION)));
        expenseBook.setType(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_TYPE)));
        expenseBook.setProfileImagePath(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_PROFILE_IMAGE_URI)));
        expenseBook.setOwner(loadMember(cursor.getLong(cursor.getColumnIndex(OWNER_KEY))));
        expenseBook.setCreationTime(cursor.getLong(cursor.getColumnIndex(EXPENSE_BOOK_CREATION_TIME)));
    }

    private Member loadMember(long aLong) {
        MemberSQLDataAdapter sqlDataAdapter = new MemberSQLDataAdapter(mContext);
        return sqlDataAdapter.get(aLong);
    }


    public void addMembers(List<Member> members, ExpenseBook expenseBook) {
        for (Member member : members) {
            ContentValues values = new ContentValues();
            values.put(MemberExpenseBookContract.MEMBER_KEY, member.getId());
            values.put(MemberExpenseBookContract.EXPENSE_BOOK_KEY, expenseBook.getId());
            mContext.getContentResolver().insert(MemberExpenseBookContract.MEMBER_EXPENSE_BOOK_URI, values);
        }
    }

    @Override
    public long create(ExpenseBook expenseBook) {
        Uri uri = save(expenseBook);
        List paths = uri.getPathSegments();
        long id = Long.parseLong((String) paths.get(paths.size() - 1));
        expenseBook.setId(id);
        return id;
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
        return restoreContentWithId(mContext, ExpenseBook.class, ExpenseBookContract.EXPENSE_BOOK_URI, null, id);
    }

    @Override
    public List<ExpenseBook> getAll() {
        List<ExpenseBook> expenseBooks = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(ExpenseBookContract.EXPENSE_BOOK_URI,
                null, null, null, null);
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
        }
        return expenseBooks;
    }

    @Override
    public void addMember(ExpenseBook expenseBook) {

    }
}
