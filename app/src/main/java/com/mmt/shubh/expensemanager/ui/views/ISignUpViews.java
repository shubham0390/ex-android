package com.mmt.shubh.expensemanager.ui.views;

import android.support.annotation.StringRes;

import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 17/Aug/2015,
 * 7:53 AM
 * TODO:Add class comment.
 */
public interface ISignUpViews extends MVPView {
    void showProgress(@StringRes int res);

    void hideProgress();

    void setFullNameError(@StringRes int stringResId);

    void setPasswordError(@StringRes int stringResId);

    void setMobileNoError(@StringRes int stringResId);

    void setEmailAddressError(@StringRes int stringResId);

    void navigateToHome();
}
