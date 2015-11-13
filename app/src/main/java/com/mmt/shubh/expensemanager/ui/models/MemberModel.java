package com.mmt.shubh.expensemanager.ui.models;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;

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

    Context mContext;

    @Inject
    MemberDataAdapter mMemberDataAdapter;

    @Inject
    public MemberModel(Context context,MemberDataAdapter memberDataAdapter) {
        mContext = context;
        mMemberDataAdapter = memberDataAdapter;
    }


   public Observable<Member> getMemberDetails(long id) {
        return Observable.create(subscriber -> {
            Member member = mMemberDataAdapter.get(id);
            subscriber.onNext(member);
            subscriber.onCompleted();
        });
    }

   public Observable<List<Member>> getAllMembers() {
        return Observable.create(subscriber -> {
            List<Member> members = mMemberDataAdapter.getAll();
            subscriber.onNext(members);
            subscriber.onCompleted();
        });
    }

}
