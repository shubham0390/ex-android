package com.mmt.shubh.expensemanager.member;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.MemberExpense;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 8:40 AM
 * TODO:Add class comment.
 */
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
        return mMemberDataAdapter.get(id);
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
        return mMemberDataAdapter.deleteMemberFromExpenseBook(memberId, expenseBookId);
    }

    public Observable<List<MemberExpense>> getMemberExpenses(long id, long id2) {
        return mExpenseDataAdapter.getSharedExpenseDetails(id, id2);
    }
}
