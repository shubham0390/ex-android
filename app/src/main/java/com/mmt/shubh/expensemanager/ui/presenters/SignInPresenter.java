package com.mmt.shubh.expensemanager.ui.presenters;

import com.mmt.shubh.expensemanager.ui.models.LoginModelImpl;
import com.mmt.shubh.expensemanager.ui.models.api.ILoginModel;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.views.ISignInView;

/**
 * Created by Subham Tyagi,
 * on 14/Aug/2015,
 * 5:39 PM
 * TODO:Add class comment.
 */
public class SignInPresenter extends MVPAbstractPresenter<ISignInView> implements MVPPresenter<ISignInView>, ILoginModel.LoginModelCallback {

    private ILoginModel mLoginModel;

    public SignInPresenter() {
        mLoginModel = new LoginModelImpl(this);

    }

    public void validateCredentials(String emailAddress, String password) {
        mLoginModel.login(emailAddress, password);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onUsernameError() {
        getView().setUsernameError();
    }

    @Override
    public void onPasswordError() {
        getView().setPasswordError();
    }

    @Override
    public void onSuccess() {
        getView().navigateToHome();
    }

    @Override
    public void onInvalidCredential() {
        getView().setInvalidCredentialError();
    }
}
