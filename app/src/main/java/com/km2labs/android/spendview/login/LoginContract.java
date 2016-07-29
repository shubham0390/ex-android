package com.km2labs.android.spendview.login;

import android.support.annotation.StringRes;

import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.mvp.MVPView;
import com.km2labs.android.spendview.setup.ProfileFetcher;

import rx.Observable;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

public interface LoginContract {

    interface View extends MVPView {

        void showProgress();

        void hideProgress();

        void navigateToHome();

        void showError(@StringRes int messageRes);

        void showAskMobileScreen();
    }

    interface Presenter extends MVPPresenter<View> {

    }

    interface Model {
        Observable<Boolean> registerUserWithSocial(ProfileFetcher profileFetcher);
    }

}
