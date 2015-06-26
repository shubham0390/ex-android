package com.mmt.shubh.expensemanager.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.ui.activity.MemberDetailActivity;
import com.mmt.shubh.expensemanager.ui.adapters.MemberListAdapter;
import com.mmt.shubh.expensemanager.ui.fragment.base.AppFragment;
import com.mmt.shubh.expensemanager.ui.fragment.base.BaseFragment;

/**
 * Created by Subham Tyagi,
 * on 22/Jun/2015,
 * 11:30 PM
 * TODO:Add class comment.
 */
public class MemberListFragment extends BaseFragment implements AppFragment, MemberListAdapter.OnMemberItemClickListener {

    private RecyclerView mRecyclerView;
    private MemberListAdapter mListAdapter;
    private TextView mEmptyText;
    private ProgressBar mProgressBar;

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new MemberListAdapter(null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.member_list);
        mEmptyText = (TextView)view.findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setAdapter(mListAdapter);
        getLoaderManager().restartLoader(12, null, mLoaderCallbacks);
    }

    @Override
    public void onMemberItemClick(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(MemberContract._ID));
        String name = cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_NAME));
        Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
        intent.putExtra(Constants.KEY_MEMBER_ID, id);
        intent.putExtra(Constants.KEY_MEMBER_NAME,name);

        startActivity(intent);
    }

    public static class MemberListLoader extends CursorLoader {

        public MemberListLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            super(context, uri, projection, selection, selectionArgs, sortOrder);
        }

        @Override
        public Cursor loadInBackground() {
            return super.loadInBackground();
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new MemberListLoader(getActivity(), MemberContract.MEMBER_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mProgressBar.setVisibility(View.GONE);
            mListAdapter.swapCursor(data);
            if (data != null || data.getCount() > 0) {
                mEmptyText.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };

}
