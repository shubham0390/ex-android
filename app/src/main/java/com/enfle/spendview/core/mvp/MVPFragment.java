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

package com.enfle.spendview.core.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.enfle.spendview.core.fragments.DaggerFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public abstract class MVPFragment<P extends MVPPresenter> extends DaggerFragment implements MVPView {

    protected P mPresenter;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(getClass().getName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        if (mPresenter == null) {
            throw new IllegalStateException("Presenter is not initialized");
        }
        mPresenter.subcribe(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubcribe(getRetainInstance());
    }
}