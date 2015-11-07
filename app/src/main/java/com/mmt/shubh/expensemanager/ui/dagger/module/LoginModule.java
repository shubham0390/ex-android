package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;
import com.mmt.shubh.expensemanager.ui.models.SigUpModelImpl;
import com.mmt.shubh.expensemanager.ui.models.api.ISignUpModel;
import com.mmt.shubh.expensemanager.ui.presenters.LoginActivityPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.SignInPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.SignUpPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 5:38 PM
 * TODO:Add class comment.
 */

@Module
public class LoginModule {

    @Provides
    @ActivityScope
    ISignUpModel providerSignUpModel(Context context, MemberRestService restService) {
        return new SigUpModelImpl(context, restService);
    }

    @Provides
    @ActivityScope
    SignInPresenter provideSignInPresenter() {
        return new SignInPresenter();
    }

    @Provides
    @ActivityScope
    SignUpPresenter provideSignUpPresenter(ISignUpModel signUpModel) {
        return new SignUpPresenter(signUpModel);
    }

    @Provides
    @ActivityScope
    LoginActivityPresenter provideLoginActivityPresenter(Context context,ISignUpModel signUpModel) {
        return new LoginActivityPresenter(context,signUpModel);
    }
    @Provides
    @ActivityScope
    public MemberRestService provideMemberRestService(Retrofit retrofit) {
        return retrofit.create(MemberRestService.class);
    }




}
