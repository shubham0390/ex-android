package com.mmt.shubh.expensemanager.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.ui.activity.MemberDetailActivity;
import com.mmt.shubh.expensemanager.ui.adapters.MemberListAdapter;
import com.mmt.shubh.expensemanager.ui.adapters.base.ListRecyclerView;
import com.mmt.shubh.expensemanager.ui.fragment.base.AppFragment;
import com.mmt.shubh.expensemanager.ui.view.DividerItemDecoration;

import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends Fragment implements AppFragment{

    private ListRecyclerView mRecyclerView;
    private MemberListAdapter mListAdapter;
    private TextView mEmptyText;
    private ProgressBar mProgressBar;
    private boolean mIsMemberDeletable;


    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new MemberListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_list, container, false);
        mRecyclerView = (ListRecyclerView) view.findViewById(R.id.member_list);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_devider_line)));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            mIsMemberDeletable = bundle.getBoolean(Constants.KEY_DELETE_MEMBER);
        }
        mListAdapter.setCanDelete(mIsMemberDeletable);
        mRecyclerView.setOnItemClickListener(new ListRecyclerView.OnItemClickListener() {
            @Override
            public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
                onMemberItemClick(id);
                return false;
            }
        });
        getLoaderManager().restartLoader(12, bundle, mLoaderCallbacks).forceLoad();
    }

    private void onMemberItemClick(long id) {
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        intent.putExtra(Constants.KEY_MEMBER_ID, id);
        startActivity(intent);
    }

    public static class MemberListLoader extends AsyncTaskLoader<List<Member>> {
        long mId;

        public MemberListLoader(Context context, long expenseBookId) {
            super(context);
            mId = expenseBookId;
        }

        @Override
        public List<Member> loadInBackground() {
            MemberDataAdapter dataAdapter = new MemberSQLDataAdapter(getContext());
            if (mId != -1) {
                return dataAdapter.getAllMemberByExpenseBookId(mId);
            }
            return dataAdapter.getAll();
        }
    }

    private LoaderManager.LoaderCallbacks<List<Member>> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Member>>() {
        @Override
        public Loader<List<Member>> onCreateLoader(int id, Bundle args) {
            if (args != null) {
                long exbId = args.getLong(Constants.KEY_EXPENSE_BOOK_ID);
                return new MemberListLoader(getActivity(), exbId);
            }
            return new MemberListLoader(getActivity(), -1);
        }

        @Override
        public void onLoadFinished(Loader<List<Member>> loader, List<Member> data) {
            mProgressBar.setVisibility(View.GONE);
            mListAdapter.setMembers(data);
            mRecyclerView.setAdapter(mListAdapter);
            if (data != null || !data.isEmpty()) {
                mEmptyText.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Member>> loader) {
        }
    };

}
