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

package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class SharedExpenseFragmentPresenter extends BasePresenter<SharedExpenseView>
        implements MVPPresenter<SharedExpenseView> {

    private ExpenseModel mExpenseModel;

    public SharedExpenseFragmentPresenter(ExpenseModel mExpenseModel) {
        this.mExpenseModel = mExpenseModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void getExpenseBookByMemberId(long memberId) {
        mExpenseModel.loadAllExpenseBookForMember(memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> getView().onExpenseBookListLoad(data), this::showError);
    }

    public void getExpenseBook(long expenseBookId) {
        mExpenseModel.getExpenseBook(expenseBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(expenseBook -> {
                    getView().onExpenseBookLoad(expenseBook);
                });
    }

    private void showError(Throwable throwable) {
        Timber.tag(throwable.getMessage());
    }
}
