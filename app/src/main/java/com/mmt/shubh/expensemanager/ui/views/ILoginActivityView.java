package com.mmt.shubh.expensemanager.ui.views;

import android.support.annotation.StringRes;

import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

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

    void setInvalidCredentialError();

    void showError(@StringRes int messageRes);
}
