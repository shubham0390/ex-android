package com.mmt.shubh.expensemanager.onboarding;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.UserInfo;

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
