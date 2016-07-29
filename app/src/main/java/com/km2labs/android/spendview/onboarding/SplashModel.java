package com.km2labs.android.spendview.onboarding;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.content.UserInfo;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@ConfigPersistent
public class SplashModel {

    private UserInfoDataAdapter userInfoDataAdapter;
    private ExpenseBookDataAdapter expenseBookDataAdapter;

    @Inject
    public SplashModel(UserInfoDataAdapter userInfoDataAdapter, ExpenseBookDataAdapter expenseBookDataAdapter) {
        this.userInfoDataAdapter = userInfoDataAdapter;
        this.expenseBookDataAdapter = expenseBookDataAdapter;

    }

    public Observable<List<UserInfo>> getUserInfo() {
        //Assuming first row of the table
        return userInfoDataAdapter.getAll();
    }

    public Observable<List<ExpenseBook>> getPrivateExpenseBook() {
        return expenseBookDataAdapter.getPrivateExpenseBook();
    }
}
