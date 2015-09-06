package com.mmt.shubh.expensemanager.ui.views;

import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 14/Aug/2015,
 * 5:32 PM
 * TODO:Add class comment.
 */
public interface ISignInView extends MVPView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void setInvalidCredentialError();
}
