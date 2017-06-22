package com.enfle.spendview.splash;

import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.MVPView;


/**
 * Created by subhamtyagi on 21/06/17.
 */

public class SplashContract {

    public interface Presenter extends MVPPresenter<View> {

        void checkLoginStatus();
    }

    public interface View extends MVPView {
        void showLoginScreen();

        void showHomeScreen();
    }
}
