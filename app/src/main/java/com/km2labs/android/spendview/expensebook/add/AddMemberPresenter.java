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

package com.km2labs.android.spendview.expensebook.add;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.expensebook.ExpenseBookModel;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.member.ContactsMetaData;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ConfigPersistent
public class AddMemberPresenter extends BasePresenter<AddUpdateExpenseView>
        implements MVPPresenter<AddUpdateExpenseView> {

    ExpenseBookModel mExpenseBookModel;

    @Inject
    public AddMemberPresenter(ExpenseBookModel expenseBookModel) {
        mExpenseBookModel = expenseBookModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    public void addMembersToExpenseBook(List<ContactsMetaData> contactsMetaDataList, List<Integer> selectedItems, long id) {
        mExpenseBookModel.addMemberToExpenseBook(contactsMetaDataList, selectedItems, id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> getView().onMemberAdd(v), e -> getView().onMemberAddError(e));

    }


}
