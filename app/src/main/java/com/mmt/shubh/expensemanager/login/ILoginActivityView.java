package com.mmt.shubh.expensemanager.login;

import android.support.annotation.StringRes;

import com.mmt.shubh.expensemanager.core.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 14/Aug/2015,
 * 5:32 PM
 * TODO:Add class comment.
 */
public interface ILoginActivityView extends MVPView {
    void showProgress();

    void hideProgress();

    void navigateToHome();

    void showError(@StringRes int messageRes);

    void showAskMobileScreen();
}
