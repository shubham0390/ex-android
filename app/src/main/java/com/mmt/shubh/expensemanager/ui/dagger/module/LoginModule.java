package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseSqlDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberExpenseSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.TransactionSQLDataAdapter;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;
import com.mmt.shubh.expensemanager.ui.activity.LoginActivity;
import com.mmt.shubh.expensemanager.ui.models.ExpenseModel;
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
    LoginActivity mLoginActivity;

    public LoginModule(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

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
    LoginActivityPresenter provideLoginActivityPresenter(Context context, ISignUpModel signUpModel) {
        return new LoginActivityPresenter(mLoginActivity, signUpModel);
    }

    @Provides
    @ActivityScope
    public MemberRestService provideMemberRestService(Retrofit retrofit) {
        return retrofit.create(MemberRestService.class);
    }

    @Provides
    @ActivityScope
    public AccountDataAdapter provideAccountDataAdapter(Context context) {
        return new AccountSQLDataAdapter(context);
    }

    @Provides
    @ActivityScope
    public ExpenseDataAdapter provideExpenseDataAdapter(Context context) {
        return new ExpenseSqlDataAdapter(context);
    }

    @Provides
    @ActivityScope
    public TransactionDataAdapter provideTransactionDataAdapter(Context context) {
        return new TransactionSQLDataAdapter(context);
    }

    @Provides
    @ActivityScope
    public MemberExpenseDataAdapter provideMemberExpenseDataAdapter(Context context) {
        return new MemberExpenseSQLDataAdapter(context);
    }

    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(Context context) {
        return new ExpenseModel(context);
    }


}
