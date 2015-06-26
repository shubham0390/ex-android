package com.mmt.shubh.expensemanager.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mmt.shubh.expensemanager.ExpenseApplication;

/**
 * Created by Subham Tyagi,
 * on 16/Jun/2015,
 * 6:40 PM
 * TODO:Add class comment.
 */
public class ToolBarActivity extends AppCompatActivity {

    protected Tracker mTracker;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        // Obtain the shared Tracker instance.
        ExpenseApplication application = (ExpenseApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }


    @Override
    protected void onResume() {
        super.onResume();
       /* mTracker.setScreenName("Image~" + getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }



}
