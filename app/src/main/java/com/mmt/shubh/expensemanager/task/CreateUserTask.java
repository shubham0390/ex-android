package com.mmt.shubh.expensemanager.task;

import android.content.Context;

import com.mmt.shubh.expensemanager.UserSettings;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;

public class CreateUserTask extends AbstractTask {

    private String TAG = getClass().getName();

    public final static String ACTION_CREATE_USER = "com.mmt.shubh.ACTION_CREATE_USER";

    private UserInfo mUserInfo;

    private MemberRestService memberRestService;

    private String mFullName;
    private String mEmailAddress;
    private String mMobileNumber;
    private String mPassword;


    public CreateUserTask(Context context, String fullName, String emailAddress, String password,
                          String mobileNo, MemberRestService memberRestService) {
        super(context);
        mFullName = fullName;
        mEmailAddress = emailAddress;
        mMobileNumber = password;
        mPassword = mobileNo;
        this.memberRestService = memberRestService;
    }

    public CreateUserTask(Context mContext, UserInfo userInfo, MemberRestService memberRestService) {
        super(mContext);
        mUserInfo = userInfo;
        this.memberRestService = memberRestService;
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

    public Member createMember(UserInfo userInfo, Context context) {
        Member member = new Member();
        if (userInfo != null) {
            member.setCoverPhotoUrl(userInfo.getCoverPhotoUrl());
            member.setMemberEmail(userInfo.getEmailAddress());
            member.setMemberName(userInfo.getDisplayName());
            member.setProfilePhotoUrl(userInfo.getProfilePhotoUrl());
            member.setMemberPhoneNumber(userInfo.getPhoneNumber());
        }
        MemberSQLDataAdapter sqlMemberDataAdapter = new MemberSQLDataAdapter(context);
        sqlMemberDataAdapter.create(member);

        return member;
    }


    @Override
    public TaskResult execute() {
        return registerUser();
    }

    private TaskResult registerUser() {
        if (NetworkUtility.isConnected(mContext)) {

            if (mUserInfo == null) {
                mUserInfo = createUser();
            }
            UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
            long id = sqlDataAdapter.create(mUserInfo);
            if (id > 0) {
                mTaskResult.setIsSuccess(true);
                Logger.debug(TAG, "user created successfully");
                UserSettings userSettings = UserSettings.getInstance();
                userSettings.setUserInfo(mUserInfo);
                createMember(mUserInfo, mContext);
               /* memberRestService.registerMember(createMember(mUserInfo, mContext))
                        .subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate())
                        .subscribe(memberObserver);*/
            } else {
                mTaskResult.setIsSuccess(false);
                Logger.debug(TAG, "user creation failed");
            }
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