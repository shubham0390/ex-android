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

package com.enfle.spendview.member.list;

import com.enfle.spendview.core.RecyclerItemView;
import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.member.MemberModel;
import com.enfle.spendview.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class MemberListFragmentPresenter extends BasePresenter<MemberListContract.View> implements MemberListContract.Presenter {

    private MemberModel mMemberModel;

    public MemberListFragmentPresenter(MemberModel memberModel) {
        mMemberModel = memberModel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void subcribe(MemberListContract.View view) {

    }

    @Override
    public void loadAllMembers() {
        mMemberModel.getAllMembers()
                .flatMap(members -> {
                    List<RecyclerItemView> itemViews = new ArrayList<>();
                    Observable.just(members).forEach(members1 -> itemViews.add(new MemberRecyclerItem()));
                    return Observable.just(itemViews);
                })
                .compose(RxUtils.applyMainIOSchedulers())
                .subscribe(getView()::setData, e -> getView().showError(e, false));
    }


    @Override
    public void loadAllMembersByExpenseBook(long expenseBookId) {
        mMemberModel.loadAllMemberByExpenseBookId(expenseBookId)
                .flatMap(members -> {
                    List<RecyclerItemView> itemViews = new ArrayList<>();
                    Observable.just(members).forEach(members1 -> itemViews.add(new MemberRecyclerItem()));
                    return Observable.just(itemViews);
                })
                .compose(RxUtils.applyMainIOSchedulers())
                .subscribe(d -> getView().setData(d), e -> getView().showError(e, false));
    }

    @Override
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
