package com.mmt.shubh.expensemanager.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;

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

    protected void injectDependencies(MainComponent mainComponent) {

    }
}