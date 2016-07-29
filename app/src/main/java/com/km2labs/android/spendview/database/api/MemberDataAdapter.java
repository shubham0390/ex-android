package com.km2labs.android.spendview.database.api;

import com.km2labs.android.spendview.database.content.Member;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface MemberDataAdapter extends IDataAdapter<Member> {



    Observable<List<Member>> getAllMemberByExpenseBookId(long expenseBookId);

    boolean deleteMemberFromExpenseBook(long memberId, long expenseBookId);
}
