package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter extends DataAdapter<ExpenseBook> {

    void addMember(ExpenseBook t);

    void addMembers(List<Member> memberList, long expenseBookId);

    void addMembers(List<Member> members, ExpenseBook expenseBook);
}
