package com.km2labs.android.spendview.core.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.view.View;

import com.km2labs.android.spendview.App;
import com.km2labs.android.spendview.core.BaseFragmentV2;
import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponentV2;

/**
 * Created by : Subham Tyagi
 * Created on :  13/09/16.
 */

public abstract class DaggerFragment extends BaseFragmentV2 {

    public static final LongSparseArray<Object> sPersistentComponentLookup = new LongSparseArray<>();

    private Long mComponentId;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        outState.putLong(getClass().getSimpleName(), mComponentId);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectDependency(savedInstanceState);
    }

    @NonNull
    public final <T> T getComponent(@Nullable Bundle savedInstanceState) {
        T configPersistentComponent;

        mComponentId = savedInstanceState == null ? App.NEXT_ID.getAndIncrement() : savedInstanceState.getLong(getClass().getSimpleName());

        configPersistentComponent = (T) sPersistentComponentLookup.get(mComponentId);

        if (configPersistentComponent == null) {

            configPersistentComponent = createComponent(App.getAppComponent().plus());

            if (configPersistentComponent == null) {
                throw new IllegalStateException("Component can't be null. Override createComponent.");
            }
            sPersistentComponentLookup.put(mComponentId, configPersistentComponent);
        }
        return configPersistentComponent;
    }

    protected <T> T createComponent(ConfigPersistentComponentV2 plus) {
        return null;
    }

    public <T> void injectDependency(@Nullable Bundle t) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!getActivity().isChangingConfigurations()) {
            sPersistentComponentLookup.remove(mComponentId);
        }
    }
}
