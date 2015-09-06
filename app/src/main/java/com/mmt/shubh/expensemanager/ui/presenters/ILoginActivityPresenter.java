package com.mmt.shubh.expensemanager.ui.presenters;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.login.ILoginHelper;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 04/Sep/2015,
 * 5:16 PM
 * TODO:Add class comment.
 */
public interface ILoginActivityPresenter<V extends MVPView> extends MVPPresenter<V> {

    void onActivityResult(int requestCode, int responseCode, Intent intent);

    void setupGoogleLogin(SignInButton plusSignInButton, Activity activity);

    void setupFacebookLogin(LoginButton faceBookLoginButton);

    void socialSignUp(ILoginHelper loginHelper);
}
