/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.core.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km2labs.android.spendview.core.base.BaseFragment;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import timber.log.Timber;

/**
 * A base fragment that uses Icepick, Butterknife and FragmentArgs.
 * Instead of overriding {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} you can also
 * simply have
 * override {@link #getLayoutRes()} and return your desired layout resource, which will be
 * inflated.
 * <p>
 * Future initialization can be done in {@link #onViewCreated(View, Bundle)} method <b>(don't
 * forget
 * to
 * call super.onViewCreated())</b>, which is called after
 * the view has been created, Butterknife has "injected" views, FragmentArgs has been set and
 * Icepick has restored savedInstanceState.
 * <code>init()</code> is called from {@link #onViewCreated(View, Bundle)} which is called after
 * {@link #onCreateView(LayoutInflater, ViewGroup,
 * Bundle)}
 * </p>
 * <p/>
 * <p>
 * If you want to use dependency injection libraries like dagger you can override {@link
 * #injectDependencies(MainComponent)} and implement dependency injection right there
 * </p>
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class MVPFragment<P extends MVPPresenter> extends BaseFragment implements MVPView {

    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        Timber.tag(getClass().getName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutRes = getLayoutRes();
        if (layoutRes == 0) {
            throw new IllegalArgumentException(
                    "getLayoutRes() returned 0, which is not allowed. "
                            + "If you don't want to use getLayoutRes() but implement your own view for this "
                            + "fragment manually, then you have to override onCreateView();");
        } else {
            View v = inflater.inflate(layoutRes, container, false);

            return v;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(getRetainInstance());
    }

    /**
     * Return the layout resource like R.layout.my_layout
     *
     * @return the layout resource or zero ("0"), if you don't want to have an UI
     */
    @LayoutRes
    protected int getLayoutRes() {
        return 0;
    }

}