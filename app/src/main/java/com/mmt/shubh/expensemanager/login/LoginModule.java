package com.mmt.shubh.expensemanager.login;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
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
    LoginActivity mLoginActivity;

    public LoginModule(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    @Provides
    @ActivityScope
    ISignUpModel providerSignUpModel(Context context, MemberRestService restService,
                                     ExpenseBookDataAdapter expenseBookDataAdapter, ExpenseModel expenseModel,
                                     UserInfoDataAdapter userInfoDataAdapter,CategoryDataAdapter categoryDataAdapter) {
        return new SigUpModelImpl(context, expenseBookDataAdapter, expenseModel, userInfoDataAdapter, restService,categoryDataAdapter);
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
    LoginActivityPresenter provideLoginActivityPresenter(ISignUpModel signUpModel, UserInfoDataAdapter userInfoDataAdapter) {
        return new LoginActivityPresenter(mLoginActivity, signUpModel, userInfoDataAdapter);
    }

    @Provides
    @ActivityScope
    public MemberRestService provideMemberRestService(Retrofit retrofit) {
        return retrofit.create(MemberRestService.class);
    }


    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                                            MemberExpenseDataAdapter memberDataAdapter,
                                            TransactionDataAdapter transactionDataAdapter,
                                            AccountDataAdapter accountDataAdapter, MemberDataAdapter dataAdapter) {
        return new ExpenseModel(expenseDataAdapter, memberDataAdapter, transactionDataAdapter,
                accountDataAdapter, dataAdapter);
    }


}
