package com.mmt.shubh.expensemanager.dagger;

import com.mmt.shubh.expensemanager.ui.activity.LoginActivity;
import com.mmt.shubh.expensemanager.ui.activity.SplashActivity;

/**
 * Created by Subham Tyagi,
 * on 28/Aug/2015,
 * 2:45 PM
 * TODO:Add class comment.
 */
public interface DaggerObjectGraph {

    void inject(SplashActivity mainActivity);

    void inject(LoginActivity repositoriesListActivity);
}
