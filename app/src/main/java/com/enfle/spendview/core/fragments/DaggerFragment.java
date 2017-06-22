package com.enfle.spendview.core.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by : Subham Tyagi
 * Created on :  13/09/16.
 */

public abstract class DaggerFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
