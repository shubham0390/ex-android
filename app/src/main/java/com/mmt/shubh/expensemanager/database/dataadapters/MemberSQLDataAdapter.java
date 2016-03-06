package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseBookContract;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 06/Jul/2015,
 * 6:31 PM
 * TODO:Add class comment.
 */
public class MemberSQLDataAdapter extends AbstractSQLDataAdapter<Member> implements MemberDataAdapter, MemberContract {


    public MemberSQLDataAdapter(BriteDatabase briteDatabase) {
        super(MemberContract.TABLE_NAME, briteDatabase);
    }

    @Override
    public Member parseCursor(Cursor cursor) {
        Member member = new Member();
        member.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        member.setMemberName(cursor.getString(cursor.getColumnIndex(MEMBER_NAME)));
        member.setMemberEmail(cursor.getString(cursor.getColumnIndex(MEMBER_EMAIL)));
        int profileUrlIndex = cursor.getColumnIndex(MEMBER_IMAGE_URI);
        if (profileUrlIndex != -1)
            member.setProfilePhotoUrl(cursor.getString(profileUrlIndex));
        int coverPhotoIndex = cursor.getColumnIndex(MEMBER_COVER_IMAGE_URL);
        if (coverPhotoIndex != -1)
            member.setCoverPhotoUrl(cursor.getString(coverPhotoIndex));

        return member;
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

    @Override
    protected void setTaskId(Member member, long id) {
        member.setId(id);
    }

    /**
     * Check if member already exists for given email and group
     *
     * @return - true if Exists otherwise falls.
     */
    public boolean isExists(Member member) {
        final boolean[] isExits = {false};
        getSingleResultByColumn(MemberContract.MEMBER_EMAIL, member.getId())
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate()).subscribe(d -> {
            isExits[0] = d != null;
        });
        return isExits[0];
    }

    @Override
    public Observable<List<Member>> getAllMemberByExpenseBookId(long expenseBookId) {

        String query = "SELECT * FROM "
                + MemberContract.TABLE_NAME
                + " WHERE "
                + MemberContract._ID
                + " IN ( SELECT "
                + MemberExpenseBookContract.MEMBER_KEY
                + " FROM "
                + MemberExpenseBookContract.TABLE_NAME
                + " WHERE "
                + MemberExpenseBookContract.EXPENSE_BOOK_KEY + " = " + expenseBookId + ");";
        return mBriteDatabase.createQuery(mTableName, query).mapToList(this::parseCursor);
    }

    @Override
    public Observable<Boolean> deleteMemberFromExpenseBook(long memberId, long expenseBookId) {
        return Observable.create(subscriber -> {
            mBriteDatabase.delete(MemberExpenseBookContract.TABLE_NAME, MemberExpenseBookContract.MEMBER_KEY + " = ?"
                            + MemberExpenseBookContract.EXPENSE_BOOK_KEY + " = ?", String.valueOf(memberId),
                    String.valueOf(expenseBookId));
            subscriber.onNext(true);
            subscriber.onCompleted();
        });
    }
}
