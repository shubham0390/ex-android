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
 * Created by Subham Tyagi,
 * on 06/Jul/2015,
 * 6:31 PM
 * TODO:Add class comment.
 */
public class MemberSQLDataAdapter extends BaseSQLDataAdapter<Member> implements MemberDataAdapter, MemberContract {


    public MemberSQLDataAdapter(Context context) {
        super(context);
    }

    public ContentValues toContentValues(Member member) {
        ContentValues values = new ContentValues();
        values.put(MEMBER_NAME, member.getMemberName());
        values.put(MEMBER_EMAIL, member.getMemberEmail());
        values.put(MEMBER_IMAGE_URI, member.getProfilePhotoUrl());
        values.put(MEMBER_COVER_IMAGE_URL, member.getCoverPhotoUrl());
        return values;
    }


    public void restore(Cursor cursor, Member member) {
        member.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        member.setMemberName(cursor.getString(cursor.getColumnIndex(MEMBER_NAME)));
        member.setMemberEmail(cursor.getString(cursor.getColumnIndex(MEMBER_EMAIL)));
        member.setProfilePhotoUrl(cursor.getString(cursor.getColumnIndex(MEMBER_IMAGE_URI)));
        member.setCoverPhotoUrl(cursor.getString(cursor.getColumnIndex(MEMBER_COVER_IMAGE_URL)));
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    /**
     * Check if member already exists for given email and group
     *
     * @param context - Application context.
     * @return - true if Exists otherwise falls.
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
        super.save(member);
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
        super.delete();
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
                cursor.close();
            }
        }
        return accountList;
    }

    @Override
    public long create(List<Member> list) {
        ContentValues[] contentValues = new ContentValues[list.size()];
        for (int i = 0; i < list.size(); i++) {
            contentValues[i] = toContentValues(list.get(i));
        }
        mContext.getContentResolver().bulkInsert(MemberContract.MEMBER_URI, contentValues);
        return 0;
    }
}
