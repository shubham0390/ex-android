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

package com.enfle.spendview.core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dagger.android.AndroidInjection;


/**
 * A simple activity that uses Butterknife and IcePick.
 * <p/>
 * <p>
 * If you want to use dependency injection libraries like dagger you can override {@link
 * </p>
 * <p>
 * Created by Subham on 30/06/16.
 */
public abstract class DaggerActivity extends ToolBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
