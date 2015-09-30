package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
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
public class MemberRealmDataAdapter extends AbsRealmDataAdapter<Member> implements MemberDataAdapter, MemberContract {


    public MemberRealmDataAdapter(Context context) {
        super(context);
    }


    private ExpenseBook getExpenseBook(long aLong) {
        return null;
    }


    @Override
    public long create(Member member) {
        super.save(member);
        return 0;
    }

    @Override
    public Member delete(Member member) {
        return null;
    }

    @Override
    public Member delete(long id) {
        return null;
    }

    @Override
    public Member get(long id) {
        return null;
    }

    @Override
    public List<Member> getAll() {
        return null;
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

                    accountList.add(account);
                }
            } finally {
                cursor.close();
            }

        }
        return accountList;
    }
}
