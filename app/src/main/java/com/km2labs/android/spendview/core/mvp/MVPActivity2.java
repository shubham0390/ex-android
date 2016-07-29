package com.km2labs.android.spendview.core.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.km2labs.android.spendview.core.dagger.DaggerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;


public abstract class MVPActivity2<P extends MVPPresenter> extends DaggerActivity {

    @Inject
    protected P mPresenter;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Icepick.restoreInstanceState(this, savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
    }

    @LayoutRes
    protected abstract int getLayout();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}