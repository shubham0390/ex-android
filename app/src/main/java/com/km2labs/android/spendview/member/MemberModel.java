package com.km2labs.android.spendview.member;

import android.database.sqlite.SQLiteConstraintException;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.MemberExpense;

import java.util.List;

import javax.inject.Inject;

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

    @Inject
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
