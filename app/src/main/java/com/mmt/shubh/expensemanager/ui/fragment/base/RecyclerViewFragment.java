package com.mmt.shubh.expensemanager.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.adapters.base.CursorRecyclerAdapter;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:16 AM
 * TODO:Add class comment.
 */
public class RecyclerViewFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    private TextView mEmptyText;
    private ProgressBar mProgressBar;
    private View mProgressContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.member_list);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressContainer = view.findViewById(R.id.progress_container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    protected void setEmptyText(int resId) {
        mEmptyText.setText(resId);
    }

    protected void setEmptyText(String emptyText) {
        mEmptyText.setText(emptyText);
    }

    protected void setAdapter(CursorRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    protected void showRecylerView(boolean value) {
        mProgressContainer.setVisibility(value ? View.GONE : View.VISIBLE);
    }

}
