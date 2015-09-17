package com.mmt.shubh.expensemanager.task;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;

public class CreateUserTask extends AbstractTask {

    private String TAG = getClass().getName();

    public final static String ACTION_CREATE_USER = "com.mmt.shubh.ACTION_CREATE_USER";

    private String mFullName;
    private String mEmailAddress;
    private String mMobileNumber;
    private String mPassword;


    public CreateUserTask(Context context, String fullName,
                          String emailAddress, String password, String mobileNo) {
        super(context);
        mFullName = fullName;
        mEmailAddress = emailAddress;
        mMobileNumber = password;
        mPassword = mobileNo;
    }


    private TaskResult createUser() {
        Logger.debug(TAG, "Adding User to database");
        TaskResult taskResult = new TaskResult();
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
        UserInfo builder = new UserInfo();
        builder.setDisplayName(mFullName);
        builder.setEmailAddress(mEmailAddress);
        builder.setUserPassword(mPassword);
        builder.setMobileNo(mMobileNumber);
        builder.setProfilePhotoUrl("");
        builder.setCoverPhotoUrl("");
        builder.setStatus(UserInfo.Status.ACTIVE);
        long id = sqlDataAdapter.create(builder);
        if (id > 0) {
            taskResult.setIsSuccess(true);
            Logger.debug(TAG, "user created successfully");
        } else {
            taskResult.setIsSuccess(false);
            Logger.debug(TAG, "user creation failed");
        }
        return taskResult;
    }

    @Override
    public TaskResult execute() {
        return createUser();
    }

    @Override
    public String getTaskAction() {
        return ACTION_CREATE_USER;
    }
}