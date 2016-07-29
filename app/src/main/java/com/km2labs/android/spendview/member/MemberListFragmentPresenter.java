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
