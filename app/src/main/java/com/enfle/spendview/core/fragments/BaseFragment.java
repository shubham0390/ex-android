package com.enfle.spendview.core.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by : Subham Tyagi
 * Created on :  28/08/16.
 */

public abstract class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getFragmentView(inflater, container);
    }

    protected abstract View getFragmentView(@NonNull LayoutInflater inflater, ViewGroup container);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    public void onNetworkError() {

    }
}
