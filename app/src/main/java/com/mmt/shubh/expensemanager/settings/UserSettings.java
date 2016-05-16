/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mmt.shubh.expensemanager.settings;

import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.UserInfo;

public class UserSettings {

    private volatile static UserSettings INSTANCE;

    private UserInfo mUserInfo;

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
        return mUserInfo.getId();
    }


    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    public void setPersonalExpenseBook(ExpenseBook d) {
        mPersonalExpenseBook = d;
    }

    public ExpenseBook getPersonalExpenseBook() {
        return mPersonalExpenseBook;
    }
}
