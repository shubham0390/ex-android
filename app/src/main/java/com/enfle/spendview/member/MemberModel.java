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

package com.enfle.spendview.member;

import android.database.sqlite.SQLiteConstraintException;

import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.database.api.ExpenseBookDataAdapter;
import com.enfle.spendview.database.api.ExpenseDataAdapter;
import com.enfle.spendview.database.api.MemberDataAdapter;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.database.content.Member;
import com.enfle.spendview.database.content.MemberExpense;
import com.enfle.spendview.expense.ExpenseListViewModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 8:40 AM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class MemberModel {


    MemberDataAdapter mMemberDataAdapter;

    ExpenseDataAdapter mExpenseDataAdapter;

    ExpenseBookDataAdapter mExpenseBookDataAdapter;

    public MemberModel(MemberDataAdapter memberDataAdapter, ExpenseDataAdapter expenseDataAdapter,
                       ExpenseBookDataAdapter expenseBookDataAdapter) {
        mMemberDataAdapter = memberDataAdapter;
        mExpenseDataAdapter = expenseDataAdapter;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
    }


    public Observable<Member> getMemberDetails(final long id) {
        return Observable.create(subscriber -> {
            subscriber.onNext(mMemberDataAdapter.get(id));
            subscriber.onCompleted();
        });

    }

    public Observable<List<Member>> getAllMembers() {
        return mMemberDataAdapter.getAll();

    }

    public Observable<List<ExpenseListViewModel>> loadAllExpenseByMemberId(long id) {
        return mExpenseDataAdapter.getExpensesWithFilters(id);
    }

    public Observable<List<ExpenseBook>> loadAllExpneseBooksByMemberId(long id) {
        return mExpenseBookDataAdapter.getByMemberId(id);
    }

    public Observable<List<Member>> loadAllMemberByExpenseBookId(long id) {
        return mMemberDataAdapter.getAllMemberByExpenseBookId(id);
    }

    public Observable<List<ExpenseListViewModel>> getAllSharedExpense(long id, long id2) {
        return mExpenseDataAdapter.getAllSharedExpenseList(id, id2);
    }

    public Observable<Boolean> deleteMemberFromExpenseBook(long memberId, long expenseBookId) {
        return Observable.create(subscriber -> {
            boolean expenseExists = mExpenseDataAdapter.isAnyExpenseExists(memberId, expenseBookId);
            if (expenseExists) {
                subscriber.onError(new SQLiteConstraintException(" Data is referenced in another table"));
            } else {
                boolean res = mMemberDataAdapter.deleteMemberFromExpenseBook(memberId, expenseBookId);
                subscriber.onNext(res);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<MemberExpense>> getMemberExpenses(long id, long id2) {
        return mExpenseDataAdapter.getSharedExpenseDetails(id, id2);
    }
}
