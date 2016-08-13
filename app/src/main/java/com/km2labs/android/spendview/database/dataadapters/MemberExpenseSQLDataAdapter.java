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

import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.content.MemberExpense;
import com.km2labs.android.spendview.database.content.contract.MemberExpenseContract;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 9:34 PM
 * TODO:Add class comment.
 */
@Singleton
public class MemberExpenseSQLDataAdapter extends BaseSQLDataAdapter<MemberExpense> implements MemberExpenseDataAdapter {


    @Inject
    public MemberExpenseSQLDataAdapter(BriteDatabase briteDatabase) {
        super(MemberExpenseContract.TABLE_NAME, briteDatabase);
    }

    @Override
    public MemberExpense parseCursor(Cursor cursor) {
        MemberExpense memberExpense = new MemberExpense();
        memberExpense.setExpenseKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.EXPENSE_KEY)));
        memberExpense.setMemberKey(cursor.getLong(cursor.getColumnIndex(MemberExpenseContract.MEMBER_KEY)));
        memberExpense.setShareAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.SHARE_AMOUNT)));
        memberExpense.setDebitAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.DEBIT_AMOUNT)));
        memberExpense.setBalanceAmount(cursor.getDouble(cursor.getColumnIndex(MemberExpenseContract.BALANCE_AMOUNT)));
        return null;
    }

    @Override
    public ContentValues toContentValues(MemberExpense memberExpense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberExpenseContract.EXPENSE_KEY, memberExpense.getExpenseKey());
        contentValues.put(MemberExpenseContract.MEMBER_KEY, memberExpense.getMemberKey());
        contentValues.put(MemberExpenseContract.SHARE_AMOUNT, memberExpense.getShareAmount());
        contentValues.put(MemberExpenseContract.DEBIT_AMOUNT, memberExpense.getDebitAmount());
        contentValues.put(MemberExpenseContract.BALANCE_AMOUNT, memberExpense.getBalanceAmount());
        return contentValues;
    }


    @Override
    protected void setId(MemberExpense memberExpense, long id) {
        //     memberExpense.set
    }
}
