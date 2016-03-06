package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter extends IDataAdapter<ExpenseBook> {

    void addMember(ExpenseBook t);

    void addMembers(List<Member> memberList, long expenseBookId);

    void addMembers(long expenseBookId, List<Long> memberList);

    void addMembers(List<Long> members, ExpenseBook expenseBook);

    Observable<List<ExpenseBook>> getByMemberId(long id);


    void addMembers(Map<Long, Long> expenseBooks);
}
