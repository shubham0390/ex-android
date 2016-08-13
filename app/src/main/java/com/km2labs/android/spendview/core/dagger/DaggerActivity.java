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

package com.km2labs.android.spendview.core.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.km2labs.android.spendview.ExpenseApplication;
import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponent;
import com.km2labs.android.spendview.core.dagger.component.DaggerConfigPersistentComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * A simple activity that uses Butterknife and IcePick.
 * <p/>
 * <p>
 * If you want to use dependency injection libraries like dagger you can override {@link
 * #injectDependencies(ConfigPersistentComponent)} and implement dependency injection right there
 * </p>
 * <p>
 * Created by Subham on 30/06/16.
 */
public abstract class DaggerActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "keyActivityId";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> PERSISTENT_COMPONENT_MAP = new HashMap<>();

    private Long mActivityId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityId = savedInstanceState == null ? NEXT_ID.getAndIncrement()
                : savedInstanceState.getLong(KEY_ACTIVITY_ID);

        ConfigPersistentComponent configPersistentComponent;
        if (!PERSISTENT_COMPONENT_MAP.containsKey(mActivityId)) {
            configPersistentComponent = DaggerConfigPersistentComponent
                    .builder()
                    .mainComponent(ExpenseApplication.get(this).getMainComponent())
                    .build();
        } else {
            configPersistentComponent = PERSISTENT_COMPONENT_MAP.get(mActivityId);
        }
        injectDependencies(configPersistentComponent);
    }

    /**
     * This method will be called from {@link #onCreate(Bundle)} and this is the right place to
     * inject
     * dependencies (i.e. by using dagger)
     */
    protected abstract void injectDependencies(ConfigPersistentComponent component);


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            PERSISTENT_COMPONENT_MAP.remove(mActivityId);
        }
    }
}
