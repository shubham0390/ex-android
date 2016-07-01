package com.mmt.shubh.expensemanager.member;

public class MemberDeleteEvent {

    public final long mMemberId;

    public MemberDeleteEvent(long mMemberId) {
        this.mMemberId = mMemberId;
    }
}
