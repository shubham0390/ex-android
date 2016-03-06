package com.mmt.shubh.expensemanager.member;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 11:53 AM
 * TODO:Add class comment.
 */
public class MemberListFragmentPresenter extends MVPAbstractPresenter<MVPLCEView<List<Member>>>
        implements MVPPresenter<MVPLCEView<List<Member>>> {

    private Context mContext;

    private MemberModel mMemberModel;

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

    public void deleteMember(long id) {

    }

    public void deleteMemberFromExpenseBook(long memberId, long expenseBookId) {
        mMemberModel.deleteMemberFromExpenseBook(memberId, expenseBookId);
        getView().loadData(false);
    }

}
