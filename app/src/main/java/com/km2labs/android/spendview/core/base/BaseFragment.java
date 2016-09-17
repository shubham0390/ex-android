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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.analytics.Tracker;
import com.km2labs.android.spendview.App;

/**
 * Created by Subham Tyagi,
 * on 21/Aug/2015,
 * 6:08 PM
 * TODO:Add class comment.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Tracker mTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity().getApplicationContext();
        // Obtain the shared Tracker instance.
        App application = (App) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onResume() {
        super.onResume();
       /* mTracker.setScreenName("Image~" + getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }
}
