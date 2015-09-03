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
 * on 17/Aug/2015,
 * 7:41 AM
 * TODO:Add class comment.
 */
public interface ISignUpPresenter<V extends MVPView> extends MVPPresenter<V> {

    void socialSignUp(ILoginHelper loginHelper);

    void userSignUp(String FullName, String EmailAddress, String password, String mobileNo);

    void onActivityResult(int requestCode, int responseCode, Intent intent);

    void setupGoogleLogin(SignInButton plusSignInButton, Activity activity);

    void setupFacebookLogin(LoginButton faceBookLoginButton);

}
