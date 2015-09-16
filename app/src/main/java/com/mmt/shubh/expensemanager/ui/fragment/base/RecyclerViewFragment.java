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
import com.mmt.shubh.expensemanager.ui.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 11:16 AM
 * TODO:Add class comment.
 */
public abstract class RecyclerViewFragment<V extends MVPView, P extends MVPPresenter<V>> extends MVPFragment<V, P> {

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.empty_text)
    TextView mEmptyText;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.progress_container)
    View mProgressContainer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    protected int getLayoutId() {
        return 0;
    }

    protected void setEmptyText(int resId) {
        mEmptyText.setText(resId);
    }

    protected void setEmptyText(String emptyText) {
        mEmptyText.setText(emptyText);
    }

    protected void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    protected void showRecylerView(boolean value) {
        mProgressContainer.setVisibility(value ? View.GONE : View.VISIBLE);
    }

}
