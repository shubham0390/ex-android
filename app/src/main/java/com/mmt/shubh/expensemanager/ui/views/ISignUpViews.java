package com.mmt.shubh.expensemanager.ui.views;

import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 17/Aug/2015,
 * 7:53 AM
 * TODO:Add class comment.
 */
public interface ISignUpViews extends MVPView {
    void showProgress();

    void hideProgress();

    void setFullNameError(int stringResId);

    void setPasswordError(int stringResId);

    void setMobileNoError(int stringResId);

    void setEmailAddressError(int stringResId);

    void navigateToHome();
}
