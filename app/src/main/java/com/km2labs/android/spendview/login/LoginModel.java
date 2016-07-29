package com.km2labs.android.spendview.login;


import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.database.content.Member;
import com.km2labs.android.spendview.database.content.UserInfo;
import com.km2labs.android.spendview.expense.ExpenseModel;
import com.km2labs.android.spendview.service.rest.service.MemberRestService;
import com.km2labs.android.spendview.setup.ProfileFetcher;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.setup.SeedDataTask;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p>
 * TODO:Add class comment.
 */
@ConfigPersistent
public class LoginModel implements LoginContract.Model {

    private ExpenseModel mExpenseModel;
    private ExpenseBookDataAdapter mExpenseBookDataAdapter;
    private UserInfoDataAdapter mUserInfoDataAdapter;
    private MemberRestService mMemberRestService;
    private CategoryDataAdapter mCategoryDataAdapter;

    @Inject
    public LoginModel(ExpenseBookDataAdapter bookDataAdapter, ExpenseModel expenseModel
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
    public Observable<Boolean> registerUserWithSocial(ProfileFetcher profileFetcher) {
        return Observable.create(subscriber -> {
            SeedDataTask seedDataTask = new SeedDataTask(mExpenseModel, mExpenseBookDataAdapter, mCategoryDataAdapter);
            UserInfo userInfo = profileFetcher.fetchUserAccountDetails();
            mUserInfoDataAdapter.create(userInfo);
            seedDataTask.execute();
        });
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
