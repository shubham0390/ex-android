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

package com.km2labs.android.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;

import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.database.content.contract.ExpenseBookContract;
import com.km2labs.android.spendview.database.content.contract.MemberExpenseBookContract;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 4:04 PM
 * TODO:Add class comment.
 */
@Singleton
public class ExpenseBookSQLDataAdapter extends BaseSQLDataAdapter<ExpenseBook> implements ExpenseBookDataAdapter, ExpenseBookContract {

    @Inject
    public ExpenseBookSQLDataAdapter(BriteDatabase briteDatabase) {
        super(ExpenseBookContract.TABLE_NAME, briteDatabase);
    }

    @Override
    public ExpenseBook parseCursor(Cursor cursor) {
        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        expenseBook.setName(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_NAME)));
        expenseBook.setDescription(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_DESCRIPTION)));
        expenseBook.setType(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_TYPE)));
        expenseBook.setProfileImagePath(cursor.getString(cursor.getColumnIndex(EXPENSE_BOOK_PROFILE_IMAGE_URI)));
        expenseBook.setOwner(cursor.getLong(cursor.getColumnIndex(OWNER_KEY)));
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
        values.put(OWNER_KEY, expenseBook.getOwnerId());
        return values;
    }


    private Member loadMember(long aLong) {
        /*MemberSQLDataAdapter sqlDataAdapter = new MemberSQLDataAdapter(mContext);
        return sqlDataAdapter.get(aLong);*/
        return null;
    }

    @Override
    protected void setId(ExpenseBook expenseBook, long id) {
        expenseBook.setId(id);
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
                + " WHERE " + MemberExpenseBookContract.MEMBER_KEY + " = " + String.valueOf(id) + " )";
        return mBriteDatabase.createQuery(mTableName, q).mapToList(this::parseCursor);
    }

    @Override
    public void addMembers(Map<Long, Long> expenseBooks) {
        Set<Long> longSet = expenseBooks.keySet();
        for (Long aLong : longSet) {
            ContentValues values = new ContentValues();
            values.put(MemberExpenseBookContract.MEMBER_KEY, aLong);
            values.put(MemberExpenseBookContract.EXPENSE_BOOK_KEY, expenseBooks.get(aLong));
            mBriteDatabase.insert(MemberExpenseBookContract.TABLE_NAME, values);
        }

    }

    @Override
    public Observable<List<ExpenseBook>> getPrivateExpenseBook() {
        return getResultByColumn(EXPENSE_BOOK_TYPE, "Private");
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
            mBriteDatabase.insert(MemberExpenseBookContract.TABLE_NAME, values);
        }
    }

    @Override
    public void addMembers(long expenseBookId, List<Long> memberList) {
        for (Long aLong : memberList) {
            ContentValues values = new ContentValues();
            values.put(MemberExpenseBookContract.MEMBER_KEY, aLong);
            values.put(MemberExpenseBookContract.EXPENSE_BOOK_KEY, expenseBookId);
            mBriteDatabase.insert(MemberExpenseBookContract.TABLE_NAME, values);
        }
    }

    @Override
    public void addMembers(List<Long> members, ExpenseBook expenseBook) {
        addMembers(expenseBook.getId(), members);
    }

}
