/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.km2labs.android.spendview.settings;

import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.login.LoginType;

public class UserSettings {

    private volatile static UserSettings INSTANCE;
    private LoginType mLoginType;
    private String mAuthToken;
    private User mUser;
    private ExpenseBook mPersonalExpenseBook;

    public static UserSettings getInstance() {
        if (INSTANCE == null) {
            synchronized (UserSettings.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserSettings();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot be cloned");
    }

    public long getUserId() {
        return mUser.getLocalId();
    }


    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setPersonalExpenseBook(ExpenseBook d) {
        mPersonalExpenseBook = d;
    }

    public ExpenseBook getPersonalExpenseBook() {
        return mPersonalExpenseBook;
    }

    public LoginType getLoginType() {
        return mLoginType;
    }

    public void setLoginType(LoginType loginType) {
        mLoginType = loginType;
    }

    public void setAuthToken(String token) {
        mAuthToken = token;
    }

    public String getAuthToken() {
        return mAuthToken;
    }
}
