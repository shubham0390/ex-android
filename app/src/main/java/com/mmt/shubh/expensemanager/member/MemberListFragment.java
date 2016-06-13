package com.mmt.shubh.expensemanager.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.expensebook.detail.DaggerExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.expensebook.detail.ExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.mvp.lce.SupportMVPLCEFragment;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends SupportMVPLCEFragment<ListRecyclerView, List<Member>, MVPLCEView<List<Member>>,
        MemberListFragmentPresenter> implements MemberListAdapterCallback {

    public static final int TYPE_EXPENSE_BOOK = 1;
    public static final int TYPE_MEMBER = 2;
    List<Member> mMemberList;
    private MemberAdapter mListAdapter;
    private boolean mIsMemberDeletable;
    private Bundle mArguments;
    private int mType = 1;
    private long mExpenseBookId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setRestoringViewState(true);
        mArguments = getArguments();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_member_list;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mArguments != null) {
            mIsMemberDeletable = mArguments.getBoolean(Constants.EXTRA_DELETE_MEMBER);
            mType = mArguments.getInt(Constants.EXTRA_TYPE, TYPE_MEMBER);
            mExpenseBookId = mArguments.getLong(Constants.EXTRA_EXPENSE_BOOK_ID);
        }
        mListAdapter = new MemberAdapter();
        mListAdapter.setCanDelete(mIsMemberDeletable);
        mContentView.setAdapter(mListAdapter);
        setupListener();
        getViewState().apply(this, true);
    }

    private void setupListener() {
        mContentView.setOnItemClickListener((parent, view, position, id) -> {
            onMemberItemClick(position);
            return true;
        });
        mContentView.setOnItemLongClickListener(((parent, view, position, id) -> {
            MemberListFragment.this.deleteMember(id);
            return true;
        }));
    }

    private void deleteMember(long id) {
        mPresenter.deleteMember(id);
    }

    private void onMemberItemClick(int position) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        Member member = mListAdapter.getItem(position);
        intent.putExtra(Constants.EXTRA_MEMBER, Parcels.wrap(member));
        startActivity(intent);
    }

    @Override
    public LCEViewState<List<Member>, MVPLCEView<List<Member>>> createViewState() {
        return new LCEViewStateImpl<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Unable to load members";
    }

    @Override
    public List<Member> getData() {
        return mMemberList;
    }

    @Override
    public void setData(List<Member> data) {
        showContent();
        mMemberList = data;
        mListAdapter.setMembers(data);

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showLoading(true);
        switch (mType) {
            case TYPE_EXPENSE_BOOK:
                mPresenter.loadAllMembersByExpenseBook(mExpenseBookId);
                break;
            case TYPE_MEMBER:
                mPresenter.loadAllMembers();
                break;
        }
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        ExpenseBookDetailComponent component = DaggerExpenseBookDetailComponent.builder()
                .memberListFragmentModule(new MemberListFragmentModule())
                .mainComponent(mainComponent).build();
        component.inject(this);
    }

    @Override
    public void onMemberDelete(long memberId, long expenseBookId) {
        mPresenter.deleteMemberFromExpenseBook(memberId, expenseBookId);
    }
}
