package com.mmt.shubh.expensemanager.ui.models;

import android.content.Context;

import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.task.CreateUserTask;
import com.mmt.shubh.expensemanager.task.ExpenseBookAndCashAccountSetupAccount;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.ProfileFetchingTask;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;
import com.mmt.shubh.expensemanager.ui.models.api.ISignUpModel;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p/>
 * TODO:Add class comment.
 */
public class SigUpModelImpl implements ISignUpModel, OnTaskCompleteListener {

    private final String TAG = getClass().getName();

    private TaskProcessor mTaskProcessor;

    private SignUpModelCallback mSignUpModelCallback;

    private Context mContext;

    public SigUpModelImpl(Context context, SignUpModelCallback signUpModelCallback) {
        mTaskProcessor = new TaskProcessor();
        mTaskProcessor.setOnTaskCompleteListener(this);
        mContext = context;
        mSignUpModelCallback = signUpModelCallback;
    }

    @Override
    public void registerUser(String fullName, String emailAddress, String password, String mobileNo) {
        Logger.debug(TAG, "Adding create user task to task processor");
        mTaskProcessor.execute(new CreateUserTask(mContext, fullName, emailAddress, password, mobileNo));
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
                mTaskProcessor.execute(new ExpenseBookAndCashAccountSetupAccount(mContext));
                break;
            case CreateUserTask.ACTION_CREATE_USER:
                Logger.debug(TAG, "User created successful .Starting setup task");
                mTaskProcessor.execute(new ExpenseBookAndCashAccountSetupAccount(mContext));
                break;
            case ExpenseBookAndCashAccountSetupAccount.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK:
                Logger.debug(TAG, "Account setup complete.Launching home activity");
                mSignUpModelCallback.onSuccess();
                break;
        }
    }
}
