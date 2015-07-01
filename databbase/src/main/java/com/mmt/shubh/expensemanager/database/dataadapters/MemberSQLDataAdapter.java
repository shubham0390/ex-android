package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import api.MemberDataAdapter;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class MemberSQLDataAdapter implements MemberDataAdapter<Member>, MemberContract {

    private Context mContext;

    public MemberSQLDataAdapter(Context context) {
        mContext = context;
    }

    public ContentValues toContentValues(Member member) {
        ContentValues values = new ContentValues();
        values.put(MEMBER_NAME, member.getMemberName());
        values.put(MEMBER_EMAIL, member.getMemberEmail());
        return values;
    }

    public void restore(Cursor cursor, Member member) {
       /* member.setId(cursor.getLong(MEMBER_ID_COLUMN));
        member.setMemberName(cursor.getString(MEMBER_NAME_COLUMN));
        member.setMemberEmail(cursor.getString(MEMBER_EMAIL_COLUMN));
        member.setExpenseBook(getExpenseBook(cursor.getLong(MEMBER_GROUP_KEY_COLUMN)));*/
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    /**
     * Check if memeber already exists for given email and group
     *
     * @param context
     * @return
     */
    public boolean isExists(Context context, Member member) {

        String SELECTION = MemberContract.MEMBER_EMAIL + "= ?";

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MEMBER_URI, BaseSQLDataAdapter.ID_PROJECTION,
                    SELECTION, new String[]{member.getMemberEmail()}, null);
            if (cursor.getCount() > 0) {
                return true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }


    @Override
    public long create(Member member) {
        return 0;
    }

    @Override
    public int update(Member member) {
        return 0;
    }

    @Override
    public int delete(Member member) {
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
    public Member get(long id) {
        return null;
    }

    @Override
    public List<Member> getAll() {
        List<Member> accountList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(MemberContract.MEMBER_URI,
                null, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Member account = new Member();
                    restore(cursor, account);
                    accountList.add(account);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return accountList;
    }

    @Override
    public long create(List<Member> list) {
        ContentValues[] contentValues = new ContentValues[list.size()];
        for (int i = 0; i > list.size(); i++) {
            contentValues[i] = toContentValues(list.get(i));
        }
        mContext.getContentResolver().bulkInsert(MemberContract.MEMBER_URI, contentValues);
        return 0;
    }
}
