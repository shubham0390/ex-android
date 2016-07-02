package com.mmt.shubh.expensemanager.member;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.core.recyclerview.GridRecyclerView;
import com.mmt.shubh.core.recyclerview.ListRecyclerView;

import java.util.List;


public class MemberGridView extends FrameLayout {

    private MemberAdapter mMemberAdapter;

    public MemberGridView(Context context) {
        super(context);
    }

    public MemberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MemberGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MemberGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context) {
        GridRecyclerView listRecyclerView = new GridRecyclerView(context);
        addView(listRecyclerView);
        mMemberAdapter = new MemberAdapter();
        mMemberAdapter.setCanDelete(false);
        listRecyclerView.setAdapter(mMemberAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public void addData(List<Member> members) {
        mMemberAdapter.setMembers(members);
    }

    public void showEmptyMessage() {
    }

    private ListRecyclerView.OnItemClickListener mItemClickListener = (parent, view, position, id) -> {
        //tyrgud
        return false;
    };
}
