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

package com.km2labs.android.spendview.core.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.analytics.Tracker;
import com.km2labs.android.spendview.ExpenseApplication;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.mvp.MVPActivity2;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 16/Jun/2015,
 * 6:40 PM
 * TODO:Add class comment.
 */
public abstract class ToolBarActivity2<P extends MVPPresenter> extends MVPActivity2<P> {

    protected Tracker mTracker;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ExpenseApplication application = (ExpenseApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Timber.tag(getClass().getName());
        initializeToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* mTracker.setScreenName("Image~" + getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    protected void initializeToolbar() {
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id 'toolbar'");
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void toggleHomeBackButton(boolean value) {
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            ab.setDisplayHomeAsUpEnabled(value);
        }
    }

}
