package com.mmt.shubh.expensemanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.ui.activity.MemberDetailActivity;
import com.mmt.shubh.expensemanager.ui.adapters.MemberListAdapter;
import com.mmt.shubh.expensemanager.ui.dagger.component.DaggerExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.ui.dagger.component.ExpenseBookDetailComponent;
import com.mmt.shubh.expensemanager.ui.dagger.module.MemberListFragmentModule;
import com.mmt.shubh.expensemanager.ui.mvp.lce.LCEViewState;
import com.mmt.shubh.expensemanager.ui.mvp.lce.LCEViewStateImpl;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.mvp.lce.SupportMVPLCEFragment;
import com.mmt.shubh.expensemanager.ui.presenters.MemberListFragmentPresenter;
import com.mmt.shubh.mmtframework.recyclerviewlib.ListRecyclerView;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends SupportMVPLCEFragment<ListRecyclerView, List<Member>, MVPLCEView<List<Member>>,
        MemberListFragmentPresenter> {

    private MemberListAdapter mListAdapter;

    List<Member> mMemberList;

    private boolean mIsMemberDeletable;

    private Bundle mArguments;

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
            mIsMemberDeletable = mArguments.getBoolean(Constants.KEY_DELETE_MEMBER);
        }
        mListAdapter =  new MemberListAdapter();
        mListAdapter.setCanDelete(mIsMemberDeletable);
        mContentView.setAdapter(mListAdapter);
        setupListener();
        getViewState().apply(this, true);
    }

    private void setupListener() {
        mContentView.setOnItemClickListener(new ListRecyclerView.OnItemClickListener() {
            @Override
            public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
                MemberListFragment.this.onMemberItemClick(id);
                return true;
            }
        });
        mContentView.setOnItemLongClickListener((new ListRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position, long id) {
                MemberListFragment.this.deleteMember(id);
                return true;
            }
        }));
    }

    private void deleteMember(long id) {
        mPresenter.deleteMember(id);
    }

    private void onMemberItemClick(long id) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        intent.putExtra(Constants.KEY_MEMBER_ID, id);
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
        showLoading(false);
        mPresenter.loadMembers(getLoaderManager(), mArguments);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        ExpenseBookDetailComponent component = DaggerExpenseBookDetailComponent.builder()
                .memberListFragmentModule(new MemberListFragmentModule())
                .mainComponent(mainComponent).build();
        component.inject(this);

    }
}
