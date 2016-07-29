package com.km2labs.android.spendview.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.km2labs.android.spendview.ExpenseApplication;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;



public class DaggerFragment extends Fragment {


    /**
     * This method will be called from {@link #onViewCreated(View, Bundle)} and this is the right place to
     * inject
     * dependencies (i.e. by using dagger)
     */
    protected void injectDependencies(MainComponent mainComponent) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainComponent component = (MainComponent) ExpenseApplication.component();
        injectDependencies(component);

    }
}
