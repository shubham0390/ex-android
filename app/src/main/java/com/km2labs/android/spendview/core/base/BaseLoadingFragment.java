package com.km2labs.android.spendview.core.base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.km2labs.android.spendview.core.view.DotsView;
import com.km2labs.expenseview.android.R;

/**
 * Created by : Subham Tyagi
 * Created on :  28/08/16.
 */

public abstract class BaseLoadingFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    //@BindView(R.id.progress_bar)
    DotsView mProgressBar;

    //@BindView(R.id.message)
    TextView mMessageTextView;

    //@BindView(R.id.retry_button)
    Button mRetryButton;

    @Override
    protected final View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.loading_fragment, container, false);
        if (enablePullToRefresh()) {
            setRefreshLayout(inflater, viewGroup);
        } else {
            View contentView = getContentView(inflater, viewGroup);
            viewGroup.addView(contentView);
        }
        return viewGroup;
    }

    private void setRefreshLayout(LayoutInflater inflater, ViewGroup viewGroup) {
        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(viewGroup.getLayoutParams());
        swipeRefreshLayout.setLayoutParams(layoutParams);
        viewGroup.addView(swipeRefreshLayout);
        View contentView = getContentView(inflater, swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.addView(contentView);
    }

    protected abstract View getContentView(LayoutInflater inflater, ViewGroup container);

    public void onLoadingStart() {
        ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(mProgressBar, DotsView.DOTS_PROGRESS, 0, 1f);
        dotsAnimator.setDuration(900);
        dotsAnimator.setRepeatMode(ValueAnimator.REVERSE);
        dotsAnimator.setRepeatCount(ValueAnimator.INFINITE);
        dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);
        dotsAnimator.start();
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setCurrentProgress(0);
    }

    public void onLoadingComplete(boolean isSuccess) {
        mProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(isSuccess ? View.GONE : View.GONE);
        mMessageTextView.setVisibility(isSuccess ? View.GONE : View.VISIBLE);
    }

    public void setMessage(@StringRes int id) {
        mMessageTextView.setVisibility(View.VISIBLE);
        mMessageTextView.setText(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }


    protected abstract void loadData();

    protected boolean enablePullToRefresh() {
        return false;
    }


    protected void showEmptyScreen() {
        // FIXME: 13/09/16 create string resource
        mMessageTextView.setText("No item exists");
    }

    @Override
    public void onRefresh() {

    }
}
