package com.mmt.shubh.expensemanager.task;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.settings.UserSettings;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CreateUserTask extends AbstractTask {

    public final static String ACTION_CREATE_USER = "com.mmt.shubh.ACTION_CREATE_USER";
    private String TAG = getClass().getName();
    private UserInfo mUserInfo;
    private UserInfoDataAdapter mUserInfoDataAdapter;
    private MemberDataAdapter mMemberDataAdapter;
    private String mFullName;
    private String mEmailAddress;
    private int mMobileNumber;
    private String mPassword;


    public CreateUserTask(Context context, String fullName, String emailAddress, String password,
                          int mobileNo, UserInfoDataAdapter userInfoDataAdapter,MemberDataAdapter memberDataAdapter) {
        super(context);
        mFullName = fullName;
        mEmailAddress = emailAddress;
        mMobileNumber = mobileNo;
        mPassword = password;
        mUserInfoDataAdapter = userInfoDataAdapter;
        mMemberDataAdapter = memberDataAdapter;
        mUserInfo = createUser();

    }

    public CreateUserTask(Context mContext, UserInfo userInfo, UserInfoDataAdapter userInfoDataAdapter,MemberDataAdapter memberDataAdapter) {
        super(mContext);
        mUserInfo = userInfo;
        mUserInfoDataAdapter = userInfoDataAdapter;
        mMemberDataAdapter = memberDataAdapter;
    }


    private UserInfo createUser() {
        Logger.debug(TAG, "Adding User to database");
        UserInfo userInfo = new UserInfo();
        userInfo.setDisplayName(mFullName);
        userInfo.setEmailAddress(mEmailAddress);
        userInfo.setUserPassword(mPassword);
        userInfo.setMobileNo(mMobileNumber);
        userInfo.setProfilePhotoUrl("");
        userInfo.setCoverPhotoUrl("");
        userInfo.setStatus(UserInfo.Status.ACTIVE);
        return userInfo;
    }

    public Member createMember(UserInfo userInfo) {
        Member member = new Member();
        if (userInfo != null) {
            member.setCoverPhotoUrl(userInfo.getCoverPhotoUrl());
            member.setMemberEmail(userInfo.getEmailAddress());
            member.setMemberName(userInfo.getDisplayName());
            member.setProfilePhotoUrl(userInfo.getProfilePhotoUrl());
            member.setMemberPhoneNumber(userInfo.getPhoneNumber());
        }
        return member;
    }


    @Override
    public TaskResult execute() {

        mMemberDataAdapter.create(createMember(mUserInfo))
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate()).subscribe(new Subscriber<Member>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Member member) {
                mUserInfo.setMemberKey(member.getId());
                mTaskResult = registerUser();
            }
        });
        return mTaskResult;
    }

    private TaskResult registerUser() {
        if (NetworkUtility.isConnected(mContext)) {

            if (mUserInfo == null) {
                mUserInfo = createUser();
            }
            mUserInfoDataAdapter.create(mUserInfo)
                    .observeOn(Schedulers.immediate())
                    .subscribeOn(Schedulers.immediate()).subscribe(d -> {
                Logger.debug(TAG, "user created successfully");
                UserSettings userSettings = UserSettings.getInstance();
                userSettings.setUserInfo(d);
            }, e -> {
                mTaskResult.setIsSuccess(false);
                Logger.debug(TAG, "user creation failed" + e.getMessage());
            });
        } else {
            mTaskResult.setIsSuccess(false);
            mTaskResult.setStatusCode(TaskResultStatus.NO_INTERNET_CONNECTION);
            mTaskResult.setException(new IllegalStateException("No internet connection"));
        }
        return mTaskResult;
    }

    @Override
    public String getTaskAction() {
        return ACTION_CREATE_USER;
    }
}