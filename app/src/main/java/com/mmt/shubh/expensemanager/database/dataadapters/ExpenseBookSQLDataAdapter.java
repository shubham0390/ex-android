package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseBookContract;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 4:04 PM
 * TODO:Add class comment.
 */
public class ExpenseBookSQLDataAdapter extends AbstractSQLDataAdapter<ExpenseBook> implements ExpenseBookDataAdapter, ExpenseBookContract {
    private Context mContext;

    public ExpenseBookSQLDataAdapter(Context context, BriteDatabase briteDatabase) {
        super(ExpenseBookContract.TABLE_NAME, briteDatabase);
        mContext = context;
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
       /* Cursor cursor = null;

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
        }*/
        return false;
    }

    @Override
    public ExpenseBook parseCursor(Cursor cursor) {
        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        expenseBook.setName(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_NAME)));
        expenseBook.setDescription(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_DESCRIPTION)));
        expenseBook.setType(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_TYPE)));
        expenseBook.setProfileImagePath(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_PROFILE_IMAGE_URI)));
        expenseBook.setOwner(loadMember(cursor.getLong(cursor.getColumnIndex(OWNER_KEY))));
        expenseBook.setCreationTime(cursor.getLong(cursor.getColumnIndex(EXPENSE_BOOK_CREATION_TIME)));
        return expenseBook;
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


    private Member loadMember(long aLong) {
        MemberSQLDataAdapter sqlDataAdapter = new MemberSQLDataAdapter(mContext);
        return sqlDataAdapter.get(aLong);
    }

    @Override
    protected void setTaskId(ExpenseBook expenseBook, long id) {

    }

    public void addMembers(List<Member> members, ExpenseBook expenseBook) {
        addMembers(members, expenseBook.getId());
    }

    @Override
    public Observable<List<ExpenseBook>> getByMemberId(long id) {

        String q = " SELECT * FROM "
                + ExpenseBookContract.TABLE_NAME
                + " WHERE " + ExpenseBookContract._ID
                + " IN  ( "
                + " SELECT "
                + MemberExpenseBookContract.EXPENSE_BOOK_KEY
                + " FROM "
                + MemberExpenseBookContract.TABLE_NAME
                + " WHERE " + MemberExpenseBookContract.MEMBER_KEY + " = " + String.valueOf(id);
        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursor);
    }

    @Override
    public void addMember(ExpenseBook expenseBook) {
        addMembers(expenseBook.getMemberList(), expenseBook.getId());
    }

    @Override
    public void addMembers(List<Member> memberList, long expenseBookId) {
        for (Member member : memberList) {
            ContentValues values = new ContentValues();
            values.put(MemberExpenseBookContract.MEMBER_KEY, member.getId());
            values.put(MemberExpenseBookContract.EXPENSE_BOOK_KEY, expenseBookId);
            mContext.getContentResolver().insert(MemberExpenseBookContract.MEMBER_EXPENSE_BOOK_URI, values);
        }
    }
}
