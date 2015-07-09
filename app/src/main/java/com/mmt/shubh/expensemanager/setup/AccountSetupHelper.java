package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.mmt.shubh.expensemanager.task.ExpenseBookAndCashAccountSetupAccount;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.ProfileFetchingTask;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;

/**
 * Created by Subham Tyagi,
 * on 02/Jul/2015,
 * 1:22 AM
 * TODO:Add class comment.
 */
public class AccountSetupHelper implements OnTaskCompleteListener {

    private TaskProcessor mTaskProcessor;

    private ProfileFetcher mProfileFetcher;

    private Context mContext;

    private AccountSetupListener mSetupListener;

    public AccountSetupHelper(Context context, ProfileFetcher profileFetcher) {
        mTaskProcessor = new TaskProcessor();
        mTaskProcessor.setOnTaskCompleteListener(this);
        mProfileFetcher = profileFetcher;
        mContext = context;
    }

    public void setSetupListener(AccountSetupListener setupListener) {
        mSetupListener = setupListener;
    }

    public void setUpUserAccount() {
        mTaskProcessor.addTask(new ProfileFetchingTask(mContext, mProfileFetcher));
        mTaskProcessor.addTask(new ExpenseBookAndCashAccountSetupAccount(mContext));
        mTaskProcessor.startExecution();
    }

    @Override
    public void onTaskComplete(String action, TaskResult taskResult) {
        switch (action) {
            case ProfileFetchingTask.ACTION_PROFILE_FETCH:
                //mSetupListener.updateProgress("Creating account");
              //  mTaskProcessor.execute(new ExpenseBookAndCashAccountSetupAccount(mContext));
                break;
            case ExpenseBookAndCashAccountSetupAccount.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK:
                mSetupListener.onAccountSetupComplete();
                break;
        }
    }


    public interface AccountSetupListener {
        void updateProgress(String message);

        void onAccountSetupComplete();
    }
}
