package com.mmt.shubh.expensemanager.login;


import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.setup.SeedDataTask;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p>
 * TODO:Add class comment.
 */
@ConfigPersistent
public class SigUpModelImpl implements ISignUpModel {

    private ExpenseModel mExpenseModel;
    private ExpenseBookDataAdapter mExpenseBookDataAdapter;
    private UserInfoDataAdapter mUserInfoDataAdapter;
    private SignUpModelCallback mSignUpModelCallback;
    private MemberRestService mMemberRestService;
    private CategoryDataAdapter mCategoryDataAdapter;

    @Inject
    public SigUpModelImpl(ExpenseBookDataAdapter bookDataAdapter, ExpenseModel expenseModel
            , UserInfoDataAdapter userInfoDataAdapter, MemberRestService memberRestService,
                          CategoryDataAdapter categoryDataAdapter) {
        this.mExpenseBookDataAdapter = bookDataAdapter;
        mExpenseModel = expenseModel;
        mUserInfoDataAdapter = userInfoDataAdapter;
        mMemberRestService = memberRestService;
        mCategoryDataAdapter = categoryDataAdapter;
        Timber.tag(getClass().getSimpleName());
    }

    @Override
    public void registerCallback(SignUpModelCallback callback) {
        mSignUpModelCallback = callback;
    }

    @Override
    public void unregisterCallback() {
        mSignUpModelCallback = null;
    }

    @Override
    public void registerUserWithSocial(ProfileFetcher profileFetcher) {
       /* SeedDataTask seedDataTask = new SeedDataTask(mExpenseModel, mExpenseBookDataAdapter, mCategoryDataAdapter);
        profileFetcher.fetchUserAccountDetails()
                .flatMap(userInfo1 -> mUserInfoDataAdapter.create(userInfo1))
                //.flatMap(userInfo2 -> mMemberRestService.registerMember(createMember(userInfo2)))
                .flatMap(userInfo -> seedDataTask.execute())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mSignUpModelCallback.onSuccess();
                }, error -> {
                    Timber.e(error.getMessage());
                    mSignUpModelCallback.onError(123);
                });*/
    }

    private Member createMember(UserInfo userInfo) {
        Member member = new Member();
        if (userInfo != null) {
            member.setCoverPhotoUrl(userInfo.getCoverPhotoUrl());
            member.setMemberEmail(userInfo.getEmailAddress());
            member.setMemberName(userInfo.getDisplayName());
            member.setProfilePhotoUrl(userInfo.getProfilePhotoUrl());
            member.setMemberPhoneNumber(String.valueOf(userInfo.getPhoneNumber()));
            Timber.d("creating member finisdhed");
        }
        return member;
    }
}
