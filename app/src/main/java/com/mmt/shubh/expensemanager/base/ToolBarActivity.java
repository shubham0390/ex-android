package com.mmt.shubh.expensemanager.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.analytics.Tracker;
import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.mvp.MVPActivity;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 16/Jun/2015,
 * 6:40 PM
 * TODO:Add class comment.
 */
public class ToolBarActivity extends MVPActivity {

    protected Tracker mTracker;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private boolean mToolbarInitialized;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ExpenseApplication application = (ExpenseApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Timber.tag(getClass().getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mToolbarInitialized) {
            throw new IllegalStateException("You must run super.initializeToolbar at " +
                    "the end of your onCreate method");
        }
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
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbarInitialized = true;
        }
    }

    protected void toggleHomeBackButton(boolean value) {
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            ab.setDisplayHomeAsUpEnabled(value);
        }
    }

}
