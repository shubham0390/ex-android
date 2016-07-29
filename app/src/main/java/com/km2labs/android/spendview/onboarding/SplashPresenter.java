package com.km2labs.android.spendview.onboarding;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.UserInfo;
import com.km2labs.android.spendview.settings.UserSettings;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by subhamtyagi on 3/15/16.
 */
@ConfigPersistent
public class SplashPresenter extends BasePresenter<SplashView> implements MVPPresenter<SplashView> {

    private SplashModel mSplashModel;

    @Inject
    public SplashPresenter(SplashModel mSplashModel) {
        this.mSplashModel = mSplashModel;
        Timber.tag(getClass().getName());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void checkLoginStatus() {
        UserInfo userInfo = UserSettings.getInstance().getUserInfo();
        if (userInfo != null) {
            getView().showHomeScreen();
        } else
            mSplashModel.getUserInfo()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::checkUserInfo, this::checkError);
    }

    private void checkUserInfo(List<UserInfo> userInfos) {
        if (userInfos != null && userInfos.size() > 0)
            checkUserInfo(userInfos.get(0));
        else
            getView().showLoginScreen();
    }

    private void checkError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void checkUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            getView().showLoginScreen();
        } else {
            UserSettings.getInstance().setUserInfo(userInfo);
            getView().showHomeScreen();
           /* mSplashModel.getPrivateExpenseBook()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(d -> {
                        UserSettings.getInstance().setPersonalExpenseBook(d.get(0));
                        getView().showHomeScreen();
                    }, this::checkError);*/
        }
    }
}
