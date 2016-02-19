package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.BaseContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
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
        super(MemberContract.MEMBER_URI, context);
    }

    public ContentValues toContentValues(Member member) {
        ContentValues values = new ContentValues();
        values.put(MEMBER_NAME, member.getMemberName());
        values.put(MEMBER_EMAIL, member.getMemberEmail());
        values.put(MEMBER_IMAGE_URI, member.getProfilePhotoUrl());
        values.put(MEMBER_COVER_IMAGE_URL, member.getCoverPhotoUrl());
        values.put(MEMBER_PHONE_NUMBER, member.getMemberPhoneNumber());
        return values;
    }


    public void restore(Cursor cursor, Member member) {
        member.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        member.setMemberName(cursor.getString(cursor.getColumnIndex(MEMBER_NAME)));
        member.setMemberEmail(cursor.getString(cursor.getColumnIndex(MEMBER_EMAIL)));
        int profileUrlIndex = cursor.getColumnIndex(MEMBER_IMAGE_URI);
        if (profileUrlIndex != -1)
            member.setProfilePhotoUrl(cursor.getString(profileUrlIndex));
        int coverPhotoIndex = cursor.getColumnIndex(MEMBER_COVER_IMAGE_URL);
        if (coverPhotoIndex != -1)
            member.setCoverPhotoUrl(cursor.getString(coverPhotoIndex));
    }

    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }

    /**
     * Check if member already exists for given email and group
     *
     * @return - true if Exists otherwise falls.
     */
    public boolean isExists(Member member) {

        String SELECTION = MemberContract.MEMBER_EMAIL + "= ?";

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MEMBER_URI, BaseContract.ID_PROJECTION,
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
        Uri uri = super.save(member);
        List paths = uri.getPathSegments();
        long id = Long.parseLong((String) paths.get(paths.size() - 1));
        member.setId(id);
        return id;
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
        return super.restoreContentWithId(Member.class, mBaseUri, null, id);
    }

    @Override
    public List<Member> getAll() {
        return restoreContent(Member.class, MemberContract.MEMBER_URI, null);
    }

    @Override
    public long create(List<Member> list) {
        List<Member> dbMemberList = getAll();
        for (Member member : list) {
            for (Member dbMember : dbMemberList)
                if (!dbMember.equals(member)) {
                    Uri uri = mContext.getContentResolver().insert(mBaseUri, toContentValues(member));
                    List<String> paths = uri.getPathSegments();
                    long id = Long.parseLong(paths.get(paths.size() - 1));
                    member.setId(id);
                } else {
                    member.setId(dbMember.getId());
                }
        }
        return 0;
    }

    @Override
    public List<Member> getAllMemberByExpenseBookId(long expenseBookId) {
        List<Member> accountList = new ArrayList<>();
        Uri uri = ContentUris.withAppendedId(ExpenseBookContract.EXPENSE_BOOK_MEMBER_URI, expenseBookId);
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
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
}
