package com.mmt.shubh.expensemanager.login;

import android.content.Context;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.api.CategoryDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.content.DeviceDetails;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.gsm.GCMTokenEvent;
import com.mmt.shubh.expensemanager.service.rest.service.MemberRestService;
import com.mmt.shubh.expensemanager.settings.UserSettings;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.task.CreateUserTask;
import com.mmt.shubh.expensemanager.task.ExpenseBookAndCashAccountSetupAccount;
import com.mmt.shubh.expensemanager.task.OnTaskCompleteListener;
import com.mmt.shubh.expensemanager.task.ProfileFetchingTask;
import com.mmt.shubh.expensemanager.task.SeedDataTask;
import com.mmt.shubh.expensemanager.task.TaskProcessor;
import com.mmt.shubh.expensemanager.task.TaskResult;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p>
 * TODO:Add class comment.
 */
public class SigUpModelImpl implements ISignUpModel, OnTaskCompleteListener {

    private final String TAG = getClass().getName();
    private ExpenseModel mExpenseModel;
    private ExpenseBookDataAdapter mExpenseBookDataAdapter;
    private UserInfoDataAdapter mUserInfoDataAdapter;
    private TaskProcessor mTaskProcessor;
    private SignUpModelCallback mSignUpModelCallback;
    private MemberRestService mMemberRestService;
    private CategoryDataAdapter mCategoryDataAdapter;
    private Context mContext;

    @Inject
    public SigUpModelImpl(Context context, ExpenseBookDataAdapter bookDataAdapter, ExpenseModel expenseModel
            , UserInfoDataAdapter userInfoDataAdapter, MemberRestService memberRestService, CategoryDataAdapter categoryDataAdapter) {
        mTaskProcessor = TaskProcessor.getTaskProcessor();
        mTaskProcessor.setOnTaskCompleteListener(this);
        mContext = context;
        this.mExpenseBookDataAdapter = bookDataAdapter;
        mExpenseModel = expenseModel;
        mUserInfoDataAdapter = userInfoDataAdapter;
        mMemberRestService = memberRestService;
        mCategoryDataAdapter = categoryDataAdapter;
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
    public void registerUser(String fullName, String emailAddress, String password, int mobileNo) {
        Logger.debug(TAG, "Adding create user task to task processor");
        mTaskProcessor.execute(new CreateUserTask(mContext, fullName, emailAddress, password,
                mobileNo, mUserInfoDataAdapter, mExpenseModel.getMemberDataAdapter()));
    }

    @Override
    public void registerUser(UserInfo userInfo) {
        Logger.debug(TAG, "Adding create user task to task processor");
        mTaskProcessor.execute(new CreateUserTask(mContext, userInfo, mUserInfoDataAdapter, mExpenseModel.getMemberDataAdapter()));
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
                //mSignUpModelCallback.onMobileNoRequired();
                mTaskProcessor.execute(new CreateUserTask(mContext, (UserInfo) taskResult.getResult()
                        , mUserInfoDataAdapter, mExpenseModel.getMemberDataAdapter()));

                break;
            case CreateUserTask.ACTION_CREATE_USER:
                Logger.debug(TAG, "User creation task complete");
                handleCreateUserTaskResult(taskResult);
                break;
            case ExpenseBookAndCashAccountSetupAccount.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK:
                Logger.debug(TAG, "Account setup complete.Launching home activity");
                TaskProcessor taskProcessor = TaskProcessor.getTaskProcessor();
                taskProcessor.execute(new SeedDataTask(mContext, mExpenseModel, mExpenseBookDataAdapter, mCategoryDataAdapter));
                break;
            case SeedDataTask.ACTION_SEED:
                Logger.debug(TAG, "Data seed Successful");
                mSignUpModelCallback.onSuccess();

        }
    }

    private void handleCreateUserTaskResult(TaskResult taskResult) {
        //registering user with server
        Logger.debug(TAG, "User created successful .Starting setup task");
        mTaskProcessor.execute(new ExpenseBookAndCashAccountSetupAccount(mContext, mExpenseModel, mExpenseBookDataAdapter));
        mSignUpModelCallback.onError(taskResult.getStatusCode());
    }

    @Subscribe
    public void onGCMTokenReceived(GCMTokenEvent event) {
        DeviceDetails deviceDetails = new DeviceDetails(mContext);
        deviceDetails.setGcmToken(event.mGCMToken);
        long memberKey = UserSettings.getInstance().getUserInfo().getMemberKey();
        mExpenseModel.getMemberDataAdapter().get(memberKey)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(member -> {
                    member.setDeviceDetails(deviceDetails);
                    Member registerMember = mMemberRestService.registerMember(member);
                    List<ExpenseBook> expenseBooks = registerMember.getExpenseBooks();
                    mExpenseBookDataAdapter.create(expenseBooks);
                    mExpenseModel.getAccountDataAdapter().create(registerMember.getAccounts());
                    //Adding seed data
                    TaskProcessor taskProcessor = TaskProcessor.getTaskProcessor();
                    taskProcessor.execute(new SeedDataTask(mContext, mExpenseModel, mExpenseBookDataAdapter, mCategoryDataAdapter));
                });
    }
}
