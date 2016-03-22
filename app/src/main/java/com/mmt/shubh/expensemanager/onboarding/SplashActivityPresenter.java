package com.mmt.shubh.expensemanager.onboarding;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.settings.UserSettings;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by subhamtyagi on 3/15/16.
 */
public class SplashActivityPresenter extends MVPAbstractPresenter<SplashView> implements MVPPresenter<SplashView> {

    private SplashModel mSplashModel;

    @Inject
    public SplashActivityPresenter(SplashModel mSplashModel) {
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
        mSplashModel.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<UserInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        checkError(e);
                    }

                    @Override
                    public void onNext(List<UserInfo> userInfos) {
                        if (userInfos != null && userInfos.size() > 0)
                            checkUserInfo(userInfos.get(0));
                        else
                            getView().showLoginScreen();
                    }
                });
    }

    private void checkError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void checkUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            getView().showLoginScreen();
        } else {
            UserSettings.getInstance().setUserInfo(userInfo);
            mSplashModel.getPrivateExpenseBook()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ExpenseBook>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<ExpenseBook> expenseBooks) {
                    UserSettings.getInstance().setPrivateExpenseBook(expenseBooks.get(0));
                    getView().showHomeScreen();
                }
            });
        }
    }
}
