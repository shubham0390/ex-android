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
import android.support.v7.app.AppCompatActivity;

import com.km2labs.android.spendview.ExpenseApplication;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;

import icepick.Icepick;

/**
 * A simple activity that uses Butterknife and IcePick.
 * <p/>
 * <p>
 * If you want to use dependency injection libraries like dagger you can override {@link
 * #injectDependencies(MainComponent)} and implement dependency injection right there
 * </p>
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public class MVPActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainComponent mainComponent = (MainComponent) ExpenseApplication.component();
        injectDependencies(mainComponent);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    /**
     * This method will be called from {@link #onCreate(Bundle)} and this is the right place to
     * inject
     * dependencies (i.e. by using dagger)
     */

    protected void injectDependencies(final MainComponent mainComponent) {

    }
}