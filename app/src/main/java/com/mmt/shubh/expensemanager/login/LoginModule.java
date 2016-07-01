package com.mmt.shubh.expensemanager.login;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;

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

    private LoginActivity mLoginActivity;

    public LoginModule(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    @Provides
    @ConfigPersistent
    ISignUpModel providerSignUpModel(MemberRestService restService,
                                     ExpenseBookDataAdapter expenseBookDataAdapter, ExpenseModel expenseModel,
                                     UserInfoDataAdapter userInfoDataAdapter, CategoryDataAdapter categoryDataAdapter) {
        return new SigUpModelImpl(expenseBookDataAdapter, expenseModel, userInfoDataAdapter, restService, categoryDataAdapter);
    }

    @Provides
    @ConfigPersistent
    public MemberRestService provideMemberRestService(Retrofit retrofit) {
        return retrofit.create(MemberRestService.class);
    }

}
