package com.enfle.spendview.core.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.enfle.spendview.AnalyticsHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by subhamtyagi on 18/01/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected AnalyticsHelper mAnalyticsHelper;

    @LayoutRes
    protected abstract int getLayout();

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Timber.tag(getClass().getName());
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


}
