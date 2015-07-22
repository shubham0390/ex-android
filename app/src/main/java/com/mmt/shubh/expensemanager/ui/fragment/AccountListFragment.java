package com.mmt.shubh.expensemanager.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.ui.activity.AccountActivity;
import com.mmt.shubh.expensemanager.ui.adapters.AccountListAdapter;
import com.mmt.shubh.expensemanager.ui.adapters.base.ListRecyclerView;
import com.mmt.shubh.expensemanager.ui.fragment.base.RecyclerViewFragment;
import com.mmt.shubh.expensemanager.ui.listener.AccountFragmentIntractionListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccountListFragment extends Fragment implements ListRecyclerView.OnItemClickListener {

    private AccountListAdapter mAccountListAdapter;
    protected RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private AccountFragmentIntractionListener mListener;

    public AccountListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.account_list);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (AccountFragmentIntractionListener) activity;
    }

    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountListAdapter = new AccountListAdapter(null);
        mRecyclerView.setAdapter(mAccountListAdapter);
        getLoaderManager().restartLoader(12, null, mLoaderCallbacks);
    }

    @Override
    public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
        long itemId = mAccountListAdapter.getItemId(position);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.KEY_ITEM_ID, itemId);
        mListener.onFragmentIntraction(AccountActivity.MODE_VIEW, bundle);
        return false;
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new AccountListLoader(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor data) {
            mAccountListAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    private static class AccountListLoader extends CursorLoader {

        public AccountListLoader(Context context) {
            super(context, AccountContract.ACCOUNT_URI, null, null, null, null);
        }
    }

}