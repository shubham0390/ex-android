package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter extends IDataAdapter<ExpenseBook> {

    void addMember(ExpenseBook t);

    void addMembers(List<Member> memberList, long expenseBookId);

    void addMembers(List<Member> members, ExpenseBook expenseBook);

    Observable<List<ExpenseBook>> getByMemberId(long id);
}
