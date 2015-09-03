package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.Member;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public interface MemberDataAdapter extends DataAdapter<Member>{

    long create(List<Member> list);
}
