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

package com.km2labs.android.spendview.member;

import android.content.Context;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class MemberListFragmentPresenter extends BasePresenter<MVPLCEView<List<Member>>>
        implements MVPPresenter<MVPLCEView<List<Member>>> {

    private Context mContext;

    private MemberModel mMemberModel;

    @Inject
    public MemberListFragmentPresenter(Context context, MemberModel memberModel) {
        mContext = context;
        mMemberModel = memberModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadAllMembers() {
        mMemberModel.getAllMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().setData(d), e -> getView().showError(e, false));
    }


    void loadAllMembersByExpenseBook(long expenseBookId) {
        mMemberModel.loadAllMemberByExpenseBookId(expenseBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().setData(d), e -> getView().showError(e, false));
    }

    public void deleteMemberFromExpenseBook(long memberId, long expenseBookId) {
        mMemberModel.deleteMemberFromExpenseBook(memberId, expenseBookId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(res -> {
                    if (res) {
                        getView().loadData(false);
                    }
                }, error -> {

                    getView().showError(error, false);

                });

    }

}
