package com.mmt.shubh.expensemanager.ui.models;

import android.content.Context;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.task.CreateUserTask;
import com.mmt.shubh.expensemanager.task.ExpenseBookAndCashAccountSetupAccount;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.ProfileFetchingTask;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;
import com.mmt.shubh.expensemanager.ui.models.api.ISignUpModel;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p>
 * TODO:Add class comment.
 */
public class SigUpModelImpl implements ISignUpModel, OnTaskCompleteListener {

    private final String TAG = getClass().getName();

    private TaskProcessor mTaskProcessor;

    private SignUpModelCallback mSignUpModelCallback;

    private MemberRestService memberRestService;

    private Context mContext;

    @Inject
    public SigUpModelImpl(Context context, MemberRestService memberRestService) {
        mTaskProcessor = TaskProcessor.getTaskProcessor();
        mTaskProcessor.setOnTaskCompleteListener(this);
        mContext = context;
        this.memberRestService = memberRestService;
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
    public void registerUser(String fullName, String emailAddress, String password, String mobileNo) {
        Logger.debug(TAG, "Adding create user task to task processor");
        mTaskProcessor.execute(new CreateUserTask(mContext, fullName, emailAddress, password, mobileNo, memberRestService));
    }

    @Override
    public void registerUserWithSocial(ProfileFetcher profileFetcher) {
        mTaskProcessor.execute(new ProfileFetchingTask(mContext, profileFetcher));
    }

    @Override
    public void onTaskComplete(String action, TaskResult taskResult) {
        switch (action) {
            case ProfileFetchingTask.ACTION_PROFILE_FETCH:
                Logger.debug(TAG, "Profile fetching successful .Starting setup task");
                mSignUpModelCallback.updateProgress(R.string.about);
                mTaskProcessor.execute(new CreateUserTask(mContext, (UserInfo) taskResult.getResult(), memberRestService));
                break;
            case CreateUserTask.ACTION_CREATE_USER:
                Logger.debug(TAG, "User creation task complete");
                handleCreateUserTaskResult(taskResult);
                break;
            case ExpenseBookAndCashAccountSetupAccount.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK:
                Logger.debug(TAG, "Account setup complete.Launching home activity");
                mSignUpModelCallback.onSuccess();
                break;
        }
    }

    private void handleCreateUserTaskResult(TaskResult taskResult) {
        if (taskResult.isSuccess()) {
            Logger.debug(TAG, "User created successful .Starting setup task");
            mTaskProcessor.execute(new ExpenseBookAndCashAccountSetupAccount(mContext));
        } else {
            mSignUpModelCallback.onError(taskResult.getStatusCode());
        }


    }
}
