package com.km2labs.android.spendview.core.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.km2labs.android.spendview.core.dagger.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham on 30/06/16.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
}
