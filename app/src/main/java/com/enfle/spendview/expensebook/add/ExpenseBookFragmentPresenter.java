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

package com.enfle.spendview.expensebook.add;

import android.text.TextUtils;

import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.database.FirebaseDataManager;
import com.enfle.spendview.database.api.exceptions.ContentNotFoundException;
import com.enfle.spendview.database.content.ExpenseBook;
import com.enfle.spendview.settings.UserSettings;
import com.enfle.spendview.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 11/Sep/2015,
 * 5:14 PM
 */
@ConfigPersistent
public class ExpenseBookFragmentPresenter extends BasePresenter<AddExpensebookFragmentContract.View>
        implements AddExpensebookFragmentContract.Presenter {

    FirebaseDataManager mFirebaseDataManager;

    @Inject
    public ExpenseBookFragmentPresenter(FirebaseDataManager firebaseDataManager) {
        mFirebaseDataManager = firebaseDataManager;
    }


    @Override
    public void validateExpenseNameAndProceed(String expenseName, String expenseDescription, boolean isUpdate) {

        if (TextUtils.isEmpty(expenseName)) {
            getView().onEmptyError();
            return;
        }

       // getView().onProgressUpdate();

        mFirebaseDataManager.getExpenseBookWithName(expenseName).compose(RxUtils.applyMainIOSchedulers())
                .subscribe(expenseBook -> {
                    if (expenseBook != null && !isUpdate) {
                        //getView().onStopProgress();
                        getView().onDuplicateExpenseBook();
                        return;
                    }
                    ExpenseBook expenseBook1 = createExpenseBook(expenseName, expenseDescription);
                    getView().onExpenseBookAdded(expenseBook1);
                    //getView().onStopProgress();
                }, throwable -> {

                    if (throwable instanceof ContentNotFoundException) {
                        ExpenseBook expenseBook = createExpenseBook(expenseName, expenseDescription);
                        //getView().onStopProgress();
                        getView().onExpenseBookAdded(expenseBook);
                        return;
                    }
                    onError(throwable);
                    getView().onStopProgress();
                });
    }

    private ExpenseBook createExpenseBook(String expenseName, String expenseDescription) {
        Map<String, Boolean> memberMap = new HashMap<>();
        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setName(expenseName);
        expenseBook.setDescription(expenseDescription);
        expenseBook.setType(ExpenseBook.TYPE_SHARED);
        expenseBook.setCreationTime(System.currentTimeMillis());
        String phoneNo = UserSettings.getInstance().getUser().getPhoneNumber();
        expenseBook.setOwnerId(phoneNo);
        memberMap.put(phoneNo, true);
        expenseBook.setMembers(memberMap);
        mFirebaseDataManager.createExpenseBook(expenseBook);
        return expenseBook;
    }
}
